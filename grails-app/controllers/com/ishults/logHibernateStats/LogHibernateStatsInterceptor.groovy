package com.ishults.logHibernateStats

import grails.core.GrailsApplication
import org.hibernate.stat.Statistics

class LogHibernateStatsInterceptor {

    GrailsApplication grailsApplication

    private static final String START_TIME = 'Hibernate_Start_Time'
    private static final String ALWAYS = 'ALWAYS'
    private static final String ALLOWED = 'ALLOWED'
    private static final String NEVER = 'NEVER'

    def sessionFactory

    LogHibernateStatsInterceptor() {
        matchAll()
    }

    boolean before() {
        String enabledString = grailsApplication.config.logHibernateStats?.enabled

        if (enabledString == NEVER) {
            return true
        }

        Statistics stats = sessionFactory.statistics
        
        if (enabledString == ALWAYS || (enabledString == ALLOWED && params._logHibernateStats)) {
            log.info "\n### Start logging for action: ${controllerName}/${actionName} ###"

            if (!stats.statisticsEnabled) {
                stats.statisticsEnabled = true
            }

            request[START_TIME] = System.currentTimeMillis()

        } else if (enabledString == ALLOWED && stats.statisticsEnabled) {
            stats.statisticsEnabled = false // We assume no one else is using stats
        }

        return true
    }

    void afterView() {
        String enabledString = grailsApplication.config.logHibernateStats?.enabled

        if (enabledString == NEVER) {
            return
        }

        if (enabledString == ALWAYS || (enabledString == ALLOWED && params._logHibernateStats)) {
            Statistics stats = sessionFactory.statistics

            if (!stats.statisticsEnabled) {
                log.error "Hibernate statistics were disabled during the action: /${controllerName}/${actionName} and could not be logged."

                return
            }

            Long end = System.currentTimeMillis()
            Long start = request[START_TIME]

            log.debug """
############## Hibernate Stats ##############
Action:                     /${controllerName}/${actionName}

Transaction Count:          ${stats.transactionCount}
Flush Count:                ${stats.flushCount}
Prepared Statement Count:   ${stats.prepareStatementCount}

Total time:                 ${end - start} ms
#############################################
"""

            stats.clear() // We assume no one else is using stats
        }
    }
}