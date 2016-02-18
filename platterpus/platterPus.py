#********************************************************************
#
# @brief - main module for managing jenkins projects
# @author - venegas, justin
# @file - platterPus.py
# @project - PlatterPus
# TODO -
#   1. Split Groovy constants to an interface
#   2. Split classes(parsing, constants ...)
#   3. Add specific exception classes(error prints > logging also)
#   4. Add DELETE option for jobs
#********************************************************************

#************************************************
# Imports
#************************************************
import ConfigParser
import subprocess

from platterPusScriptFactory import *
from platterPusParser import *
from jenkinsJob import *
#************************************************
# Constants
#************************************************
# File
DEFAULT_CONFIG_FILE =           'jenkins.cfg'
DEFAULT_GRVY_PRJCT_FILE =       './project.groovy'
DEFAULT_GRVY_TMP_ACTION_FILE =  './tmpAction.groovy'

# Curl / CLI
CURL_OPT_USER =                     '--user'
CURL_OPT_DATA =                     '--data-urlencode'
#************************************************
# Methods
#************************************************
def cleanup_project_files():
    # Remove all .groovy files in the directory
    pass
#************************************************
# Classes
#************************************************
class JenkinsProject(object):

    def __init__(self, config=DEFAULT_CONFIG_FILE, groovy_file=DEFAULT_GRVY_PRJCT_FILE):
        print("Entering %s:%s" % (self.__class__.__name__, self.__init__.__name__))
        self.config_file = config
        self.groovy_file = groovy_file
        self.parser = ConfigParser.ConfigParser()
        self.project_script = []
        self.job_list = []

        # Credentials
        self._username = None
        self._password = None

        # Jenkins Information
        self._server_address = None
        self._server_port = None
        self._uri_base = None

    def parse_config(self):
        print("Entering %s:%s" % (self.__class__.__name__, self.parse_config.__name__))
        self.parser.read(self.config_file)
        for section in self.parser.sections():
            if section == SECTION_TAG_CREDENTIALS:
                # Parse credentials
                self._username = self.parser.get(SECTION_TAG_CREDENTIALS, SECTION_FIELD_USERNAME)
                self._password = self.parser.get(SECTION_TAG_CREDENTIALS, SECTION_FIELD_PASSWORD)
            elif section == SECTION_TAG_SERVER:
                # Parse server details
                self._server_address = self.parser.get(SECTION_TAG_SERVER, SECTION_FIELD_ADDRESS)
                self._server_port = self.parser.get(SECTION_TAG_SERVER, SECTION_FIELD_PORT)
                self._uri_base = self.parser.get(SECTION_TAG_SERVER, SECTION_FIELD_URI)
            elif SECTION_TAG_JOB in section:
                # Parse job details
                new_job = self._build_job(self.parser, section)
                self.job_list.append(new_job)
            else:
                # Section type unknown
                print('ERROR: Unknown config section "%s"' % (section))
                print('ERROR: Implementation for section type "%s" needed. ' % (section))

        self._validate_members()

    def build_project(self):
        print("Entering %s:%s" % (self.__class__.__name__, self.build_project.__name__))
        # Add imports
        self.project_script.append('import %s' % GRVY_IMPORT_JNKNS_MDL)
        for job in self.job_list:
            # Add defs
            job_number = self.job_list.index(job)
            job_var_name = "jobName%s" % job_number
            job_var_config = "configXml%s" % job_number
            job_var_stream = "xmlStream%s" % job_number

            self.project_script.append('def %s = "%s"' % (job_var_name, job.name))
            self.project_script.append('def %s = \'%s\'' % (job_var_config, job.config_xml))
            self.project_script.append('def %s = new ByteArrayInputStream(%s.getBytes())' % (job_var_stream, job_var_config))
            self.project_script.append('Jenkins.instance.createProjectFromXML(%s, %s)' % (job_var_name, job_var_stream))

        # Write script to disk
        self._write_grvy_scrpt_to_disk(self.project_script, self.groovy_file)

    def create_project(self):
        print("Entering %s:%s" % (self.__class__.__name__, self.create_project.__name__))
        cli_cmd = self._build_cli_cmd(self.groovy_file)
        return self._execute_cli(cli_cmd)

    def get_job(self, job_name):
        for job in self.job_list:
            if job.name == job_name:
                return job

    def run_job(self, job):
        script = self._build_run_job(job)
        # TODO - these last three steps could be a single action? Doesn't change except for tmp file vs full file
        self._write_grvy_scrpt_to_disk(script, DEFAULT_GRVY_TMP_ACTION_FILE)
        cli_cmd = self._build_cli_cmd(DEFAULT_GRVY_TMP_ACTION_FILE)
        return self._execute_cli(cli_cmd)

    def delete_project(self):
        for job in self.job_list:
            self.delete_job(job)

    # TODO - might be cleaner just passing name. Don't need whole object
    def delete_job(self, job):
        script = self._build_delete_job(job)
        self._write_grvy_scrpt_to_disk(script, DEFAULT_GRVY_TMP_ACTION_FILE)

        # TODO - probably come up with a better system for these groovy scripts
        cli_cmd = self._build_cli_cmd(DEFAULT_GRVY_TMP_ACTION_FILE)
        return self._execute_cli(cli_cmd)

    def _execute_cli(self, cli_cmd):
        proc = subprocess.Popen(cli_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out = proc.communicate()

        std_out = out[0]
        std_err = out[1]
        print("Executing project from cli: %s" % cli_cmd)
        print("Std_Out: %s" % std_out)
        print("Std_Err: %s" % std_err)
        return std_out, std_err

    # TODO - functions like this can be static or in a 'file' class, then just import
    def _write_grvy_scrpt_to_disk(self, script, file_path):
        filewriter = open(file_path, mode='w', buffering=0)
        for line in script:
            filewriter.write("%s\n" % line)
        filewriter.close()

    def _validate_members(self):
        if self._username is None or self._password is None:
            raise Exception("Jenkins credentials were null. Is the config populated?")

        if self._server_address is None or self._server_port is None:
            raise Exception("Jenkins server address details were null. Is the config populated?")

    # TODO - think about a factory pattern 'builders' module?
    def _build_delete_job(self, job):
        script = []
        job_var_name = "job_%s" % job.name
        script.append('def %s = hudson.model.Hudson.instance.getJob("%s")' % (job_var_name, job.name))
        script.append('%s.delete()' % job_var_name)
        return script

    def _build_run_job(self, job, delay=0):
        script = []
        job_var_name = "job_%s" % job.name
        script.append('def %s = hudson.model.Hudson.instance.getJob("%s")' % (job_var_name, job.name))
        script.append('hudson.model.Hudson.instance.queue.schedule(%s, %s)' % (job_var_name, delay))
        return script

    def _build_cli_cmd(self, file_path):
        # TODO - Generalize to take a file
        cmd = []
        cmd.append("curl")
        cmd.append(CURL_OPT_USER)
        cmd.append('\'%s:%s\'' % (self._username, self._password))
        cmd.append(CURL_OPT_DATA)
        cmd.append('"script=$(<%s)"' % (file_path))
        cmd.append(self._build_uri())
        cmd_cli = " ".join(cmd)
        return cmd_cli

    def _build_job(self, parser, job_section):
        print("Entering %s:%s" % (self.__class__.__name__, self._build_job.__name__))
        name = parser.get(job_section, SECTION_FIELD_NAME)
        config_xml = parser.get(job_section, SECTION_FIELD_CONFIGXML)
        config_xml = config_xml.replace("\n", "")
        new_job = JenkinsJob(name, config_xml)
        return new_job

    def _build_uri(self):
        print("Entering %s:%s" % (self.__class__.__name__, self._build_uri.__name__))
        uri = 'http://%s:%s%s' % (self._server_address, self._server_port, self._uri_base)
        return uri

def main():
    # note - better way than calling these in serial?
    try:
        project = JenkinsProject()
        project.parse_config()
        project.build_project() # writes out script
        project.create_project() # deploys script to jenkins
        project.run_job(project.get_job("testgroovyjob")) # schedules to be run
        print('platterpus test complete')
        raw_input("testgroovyjob ran")
        job_to_delete = project.get_job("testgroovyjob")
        project.delete_job(job_to_delete)
    except Exception as e:
        print(e)

if __name__ == "__main__":
    main()
