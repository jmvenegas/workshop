#********************************************************************
#
# @brief - simplified factory pattern class for building groovy script lines
# @author - venegas, justin
# @file - platterPusScriptFactory.py
# @project - PlatterPus
#********************************************************************

#************************************************
# Imports
#************************************************

#************************************************
# Constants
#************************************************
# Groovy
GRVY_IMPORT_JNKNS_MDL =         'jenkins.model.*'

# Action
ACTION_SCHEDULE_JOB =           'RUN'
ACTION_DELETE_JOB =             'DELETE'

#************************************************
# Methods
#************************************************
def build_script(action_type):
    if action_type == ACTION_SCHEDULE_JOB:
        pass
    elif action_type == ACTION_DELETE_JOB:
        pass
#************************************************
# Classes
#************************************************
