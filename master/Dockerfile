FROM jenkins/jenkins:lts

ARG JAVA_OPTS
ENV JAVA_OPTS "-Djenkins.CLI.disabled=true ${JAVA_OPTS:-}"
ENV JENKINS_HOME /var/jenkins_home
COPY plugins_extra.txt /usr/share/jenkins/ref/plugins_extra.txt
COPY ec2_conf_plugin.groovy /var/jenkins_home/init.groovy.d/ec2_conf_plugin.groovy
RUN xargs /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins_extra.txt


