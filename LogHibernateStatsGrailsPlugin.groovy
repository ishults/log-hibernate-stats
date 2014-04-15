class LogHibernateStatsGrailsPlugin {
    // the plugin version
    def version = "0.9"

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"

    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Log Hibernate Stats Plugin" // Headline display name of the plugin
    def author = "Igor Shults"
    def authorEmail = "igor.shults@gmail.com"
    def description = '''\
A Grails plugin to log Hibernate statistics across controller actions.
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/ishults/logHibernateStats"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Any additional developers beyond the author specified above.
    def developers = [ [ name: "Igor Shults", email: "igor.shults@gmail.com" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "GitHub", url: "https://github.com/ishults/logHibernateStats/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/ishults/logHibernateStats" ]
}
