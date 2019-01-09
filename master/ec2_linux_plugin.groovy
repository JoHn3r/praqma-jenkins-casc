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
  description:              'Jenkins slave EC2 Eu West 1',
  ebsOptimized:             false,
  iamInstanceProfile:       '',
  idleTerminationMinutes:   '5',
  initScript:               '',
  instanceCapStr:           '2',
  jvmopts:                  '',
  labelString:              'aws.ec2.us.east.jenkins.slave',
  launchTimeoutStr:         '',
  numExecutors:             '1',
  remoteAdmin:              '',
  remoteFS:                 '',
  securityGroups:           'sg-030c9d6c3d6431799',
  stopOnTerminate:          false,
  subnetId:                 'subnet-0354c7e43ef868d03',
  tags:                     new EC2Tag('Name', 'jenkins-ec2-linux'),
  tmpDir:                   '',
  type:                     't2.medium',
  useDedicatedTenancy:      false,
  useEphemeralDevices:      true,
  usePrivateDnsName:        true,
  userData:                 '',
  zone:                     'eu-west-1a,eu-west-1b'
]
 
def AmazonEC2CloudParameters = [
  cloudName:      'jenkins-ec2-windows',
  credentialsId:  'central-shared-ci',
  instanceCapStr: '2',
  privateKey:     '''-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEAqFdCVwAVx/A14KhHdXs8yN46DRdScqxBB6cpclbGj8Ykj9c7borI8Q/ymDId
W5QidXQ16r9tNYaErY+mUyFqtq9o1RE+5eTBKXcvDyZ2oyfgJGWUeSdjM2gyZiNfXPw4y5Qdypw4
mwaftqt7wVyAqvFK6H/OJHO5XcUA28qwZxlaoYKYorlKpIf0LPsmE/Au2IGDmd1oc/PJbdJs4IhU
fhuBMBC/51hlcSTJRS0B0iYA1KSYIlPEAvLHoBBgrJHg4acGwTPpcpyFrn3NfJ3B814EhDzdZ8Si
aFLpvC97rEA/2idqREYMHrr8SYkOQl2V7+HQtkcd1ms/CtCX4MLicwIDAQABAoIBAEjUH3ZGKiYq
zRRZmTjBTUXVpqW9PkUwMp4d+rdlxVe0MGMuiZ7EWjS4Yornu33AM7w+v4jEsnmircnsyNdUMLfC
YYc0g9KvycWns2tJh7AEc1sruzVDDg7SeGNp0DsJP4ULmmvqdC7ViclcnRjFbUEHTRITINryWfKw
ZTOxl+myAJAgh8+2aCrxXxc/96neLemhFSeHinPqktw10tpM1tTAkBywgYFLmV1LwQgzkSIX70br
xAYGyGRk8ccTbVsGtjCWXO79saFplfrwM1ewrmUrsCebR3/n1ONGkctr+gd7CTf8EgcWdZpZVnpk
dir9evGroYGFqY3dTc9+itBK0NECgYEA6L2CNnIdSrJMKF0tiYf8FuQFtrpsIPmcprEJVmoMkYya
YpJAv6h00mfftmVMXgjRntnRYfTufxrwZ+2ZtYEFRRLrBXm/DvgAzNoWbzlMfmqdvjW5ewlkr5/l
3eBNOMOt+/Opm+07eFNnxMd3VWz4Ofai2QqezHEdSuFckpr+eZsCgYEAuSojXNC/CJMnLry3HKk6
HZvhvW17GlsQRyT5a2gjnCPrPS1sMT0sKWpk3KomPdVv2s66FZfr+nzJWp0UuhT8L/ifxDsNzv44
8y34WEJ37zIEehqC2b+vyO6yJRO6/XP86zeRyYIPa2+sBNShleDNgnsmgZi428LD5yKGVnQ5lAkC
gYEAmr5Vs0mQPDfr9vJs8oJdlAKr4eB1QD92bkZLRQ9hp+xqQZuoPUYN1k+GKMmVSvhmF04Pryeg
4xXQPel2R32vOUvow009QQCX15wxSmY4e9z7YJpr5mhKb5LKzQjzjwhbx6csYCYNYmViGy3cJAo9
yJw5jkFHb/rNVRod6MPw6n0CgYAP8BGVJp6a1Ve1DSdRvzsyWz2F+Yt0DEyNYyBY32DZSrovzN4Y
KuTHN9IwsDCUd++lzM91llsId385XnXvQmkmk+bklBSvMXCjddx/dFEwdISwIeILbhVqXSlWSal5
sWces+zHhHthSv5qeAbkiLIHviPlWcjnmY8VMQKRJHXkSQKBgQCB1ZZH/WLtIJFYAc8QM6rHC1yB
2UVcZNI+Fdp7sefdc/qHw7dcd1J2+r+jYbhzGn6x76JZBKNCCsEtRSJUju3B/D2msxTVjMU92YNO
EwMg4PdxZidyA2BOfdN8amHLYzBEwAtADpb3SEWHm5dyqrWHtC8yUSe0U5qaMS373mujsQ==
-----END RSA PRIVATE KEY-----''',
  region: 'eu-west-1',
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