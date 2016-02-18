import jenkins.model.*
def jobName0 = "testgroovyjob"
def configXml0 = '<project><keepDependencies>false</keepDependencies><properties><hudson.plugins.disk__usage.DiskUsageProperty plugin="disk-usage@0.25"/></properties><scm class="hudson.scm.NullSCM"/><canRoam>true</canRoam><disabled>false</disabled><blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding><blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding><triggers/><concurrentBuild>false</concurrentBuild><builders/><publishers/><buildWrappers/></project>'
def xmlStream0 = new ByteArrayInputStream(configXml0.getBytes())
Jenkins.instance.createProjectFromXML(jobName0, xmlStream0)
