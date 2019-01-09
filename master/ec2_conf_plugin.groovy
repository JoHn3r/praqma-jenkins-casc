import com.amazonaws.services.ec2.model.InstanceType
import com.cloudbees.jenkins.plugins.awscredentials.AWSCredentialsImpl
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.Domain
import hudson.model.*
import hudson.plugins.ec2.AmazonEC2Cloud
import hudson.plugins.ec2.AMITypeData
import hudson.plugins.ec2.EC2Tag
import hudson.plugins.ec2.SlaveTemplate
import hudson.plugins.ec2.SpotConfiguration
import hudson.plugins.ec2.UnixData
import jenkins.model.Jenkins
 
// parameters
def SlaveTemplateUsEast1Parameters = [
  ami:                      'ami-3548444c',
  associatePublicIp:        false,
  connectBySSHProcess:      true,
  connectUsingPublicIp:     false,
  customDeviceMapping:      '',
  deleteRootOnTermination:  true,
  description:              'Jenkins slave EC2 US East 1',
  ebsOptimized:             false,
  iamInstanceProfile:       '',
  idleTerminationMinutes:   '5',
  initScript:               '',
  instanceCapStr:           '2',
  jvmopts:                  '',
  labelString:              'aws.ec2.us.east.jenkins.slave',
  launchTimeoutStr:         '',
  numExecutors:             '1',
  remoteAdmin:              'centos',
  remoteFS:                 '',
  securityGroups:           'sg-11111111',
  stopOnTerminate:          false,
  subnetId:                 'subnet-SSSSSSSS',
  tags:                     new EC2Tag('Name', 'jenkins-slave'),
  tmpDir:                   '',
  type:                     't2.medium',
  useDedicatedTenancy:      false,
  useEphemeralDevices:      true,
  usePrivateDnsName:        true,
  userData:                 '',
  zone:                     'us-east-1a,us-east-1b'
]
 
def AmazonEC2CloudParameters = [
  cloudName:      'MyCompany',
  credentialsId:  'jenkins-aws-key',
  instanceCapStr: '2',
  privateKey:     '''-----BEGIN RSA PRIVATE KEY-----
MIIEogIBAAKCAQEAkjaolzS/3wlfDfQkaXyuBQ5VaodY9/0tm8BUdcfGmv7MQLUg
G12MNC5MLcR0Oj4NIOJZEZvH3afPmk5UZ1hZWRWupokSXDM3alNFoWzt0SKDTsig
nHrqouojRbTqZG8dlmAOHom3mTzj3OFG7PyLdsAEOcrVXwnqgBn7mDriXz+dLkFY
QctsJHXpm3XBNVaZ/Rmx7vAwFSg3XO0DkjRjR2mXadyh4yQX/bMqr1VEAYBBjGtZ
ri9OXAnoBMduNndeRTQ6i4xA2mBW7zcg4qksHUd+0jKN5t8cVxqOSOcCCztgwaXh
xEa9/SMIS+FR6NOrUQ+w0MxWsspHogWNWif9IQIDAQABAoIBAGLt9r5wY46OsbEo
SubRBJHJNAQjVhBdTtm+lacnp/rBggQaSYIalr3LwaGJ9jZeO9XPMQLYC7CvVamL
bFKMlc/P+t8VicW2pb0cYNWrdXH2zy+kUf/urwnSMFF+6sVP5Y4UqhkBh6d4apjO
GIZLDjYoD/PmiN6IQBGzAufql7ZntgUohYYkHM/ovskZSR6fSKXn91sirlmisfhE
/74kGfJF2+S/+HYtpcCgYkSYs/c0Ibzw8wEnNaCK+I0zn4Ppp53ip3hOiI3+0EVY
qnNisqL5yj8wjj1QFfwkVyWCtr7p0U4A4aDza35rxDKpZW/PcZNRK5pbLQzriqo5
J9DOQJ0CgYEA2HGwf+GjRMoJCcONjHKP8NJ3KoSBFj0ujJAxhIOyxJveMMS5agCH
94yNReZEppV7C/1fpcPb9GL38tfAb6VdGHOlFmq7djgkCKH+F7/PvDJ+u+1G871K
YtvEFlHT6IPUouEfSj+7/eRxZwNEuKkM2x4dOqPXbvKU63HJkxRFdz8CgYEArO89
WARI2+o82V3ldPEZAIfri+4HD0nYW7UY4hbExdyuYTKL619Wt1nr91ubCnpR5/1s
xfesBGYHlqsAuHi4tXCaU9aDyK9j+MnWUkDMvG5RXWzLDmrrfmFlohHc6r7HuVuR
gtVayj8izcZpXew6Vo3ENRdvfxCzT2V7OPnG058CgYAOb465CMCN7vepWgyPyHhH
NJJUGKBPbmczYs6aqvn6WPb5w7UmF8D5xrsJZXFAtwmM5CpD8QszgoJNBQzFpX7P
Ca+CDj5QhTAKD1vWE6n0QF3phMrNqNtUOpoabvy2Lky5TFB88EFGjrzthO9JbaT4
3EpQxeqxcKZ0CZPLJnf3mwKBgFu46IhufVZm/q8rpjBIUEJ/1Ob68LOjLyY0/2Wr
PeLUEYlsDdphTtUg1I71/12nUxoAyFiX7JzIoO3A9TjijtVtS+17sZoXrKagJxSp
We33dSBgO7MB8rWtYwJ7BvlbBwPBFYSXNPhgVE1gFzLBwI930cF3FKQIb5KE+L5X
fKVxAoGAcvNM9HpgtR3ngP7xWkeOWVkV6NDc2GbuYptbAMM7lY2DzG2Dbq1ru6iJ
n5CNoNomPrHA05Zx2e+DbmrDbJVowSlX5xJKbc3ttYsBZlqYdZmWllpG1np8snwd
I2vmggm6Uubt0s433SbMwgXonolPH0N7s8VdzVf0/moMUujYcE0=
-----END RSA PRIVATE KEY-----''',
  region: 'us-east-1',
  useInstanceProfileForCredentials: false
]
 
def AWSCredentialsImplParameters = [
  id:           'jenkins-aws-key',
  description:  'Jenkins AWS IAM key',
  accessKey:    '01234567890123456789',
  secretKey:    '01345645657987987987987987987987987987'
]
 
// https://github.com/jenkinsci/aws-credentials-plugin/blob/aws-credentials-1.23/src/main/java/com/cloudbees/jenkins/plugins/awscredentials/AWSCredentialsImpl.java
AWSCredentialsImpl aWSCredentialsImpl = new AWSCredentialsImpl(
  CredentialsScope.GLOBAL,
  AWSCredentialsImplParameters.id,
  AWSCredentialsImplParameters.accessKey,
  AWSCredentialsImplParameters.secretKey,
  AWSCredentialsImplParameters.description
)
 
// https://github.com/jenkinsci/ec2-plugin/blob/ec2-1.38/src/main/java/hudson/plugins/ec2/SlaveTemplate.java
SlaveTemplate slaveTemplateUsEast1 = new SlaveTemplate(
  SlaveTemplateUsEast1Parameters.ami,
  SlaveTemplateUsEast1Parameters.zone,
  null,
  SlaveTemplateUsEast1Parameters.securityGroups,
  SlaveTemplateUsEast1Parameters.remoteFS,
  InstanceType.fromValue(SlaveTemplateUsEast1Parameters.type),
  SlaveTemplateUsEast1Parameters.ebsOptimized,
  SlaveTemplateUsEast1Parameters.labelString,
  Node.Mode.NORMAL,
  SlaveTemplateUsEast1Parameters.description,
  SlaveTemplateUsEast1Parameters.initScript,
  SlaveTemplateUsEast1Parameters.tmpDir,
  SlaveTemplateUsEast1Parameters.userData,
  SlaveTemplateUsEast1Parameters.numExecutors,
  SlaveTemplateUsEast1Parameters.remoteAdmin,
  new UnixData(null, null, null),
  SlaveTemplateUsEast1Parameters.jvmopts,
  SlaveTemplateUsEast1Parameters.stopOnTerminate,
  SlaveTemplateUsEast1Parameters.subnetId,
  [SlaveTemplateUsEast1Parameters.tags],
  SlaveTemplateUsEast1Parameters.idleTerminationMinutes,
  SlaveTemplateUsEast1Parameters.usePrivateDnsName,
  SlaveTemplateUsEast1Parameters.instanceCapStr,
  SlaveTemplateUsEast1Parameters.iamInstanceProfile,
  SlaveTemplateUsEast1Parameters.deleteRootOnTermination,
  SlaveTemplateUsEast1Parameters.useEphemeralDevices,
  SlaveTemplateUsEast1Parameters.useDedicatedTenancy,
  SlaveTemplateUsEast1Parameters.launchTimeoutStr,
  SlaveTemplateUsEast1Parameters.associatePublicIp,
  SlaveTemplateUsEast1Parameters.customDeviceMapping,
  SlaveTemplateUsEast1Parameters.connectBySSHProcess,
  SlaveTemplateUsEast1Parameters.connectUsingPublicIp
)
 
// https://github.com/jenkinsci/ec2-plugin/blob/ec2-1.38/src/main/java/hudson/plugins/ec2/AmazonEC2Cloud.java
AmazonEC2Cloud amazonEC2Cloud = new AmazonEC2Cloud(
  AmazonEC2CloudParameters.cloudName,
  AmazonEC2CloudParameters.useInstanceProfileForCredentials,
  AmazonEC2CloudParameters.credentialsId,
  AmazonEC2CloudParameters.region,
  AmazonEC2CloudParameters.privateKey,
  AmazonEC2CloudParameters.instanceCapStr,
  [slaveTemplateUsEast1]
)
 
// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()
 
// get credentials domain
def domain = Domain.global()
 
// get credentials store
def store = jenkins.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
 
// add credential to store
store.addCredentials(domain, aWSCredentialsImpl)
 
// add cloud configuration to Jenkins
jenkins.clouds.add(amazonEC2Cloud)
 
// save current Jenkins state to disk
jenkins.save()