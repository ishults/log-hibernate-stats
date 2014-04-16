log4j = {
    error 'org.codehaus.groovy.grails',
          'org.springframework',
          'org.hibernate',
          'net.sf.ehcache.hibernate'

    info  'grails.app.filters'
}

logHibernateStats {
    enabled = 'NEVER'// From ALWAYS, ALLOWED, NEVER
}