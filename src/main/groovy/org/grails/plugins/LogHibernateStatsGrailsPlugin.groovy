package org.grails.plugins

import grails.plugins.Plugin

class LogHibernateStatsGrailsPlugin extends Plugin {
    def grailsVersion = "3.0.0 > *"
    def title = "Log Hibernate Stats Plugin"
    def description = 'Logs Hibernate statistics across controller actions'
    def documentation = "https://github.com/ishults/log-hibernate-stats"
    def license = "Apache 2.0"
    def developers = [ [ name: "Igor Shults", email: "igor.shults@gmail.com" ]]
    def issueManagement = [ system: "GitHub", url: "https://github.com/ishults/log-hibernate-stats/issues" ]
    def scm = [ url: "https://github.com/ishults/log-hibernate-stats" ]
}
