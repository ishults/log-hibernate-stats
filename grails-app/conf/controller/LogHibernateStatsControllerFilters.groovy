package controller

import org.hibernate.stat.Statistics

class LogHibernateStatsControllerFilters {

    private static final String START_TIME = 'Hibernate_Start_Time'
    private static final String ALWAYS = 'ALWAYS'
    private static final String ALLOWED = 'ALLOWED'
    private static final String NEVER = 'NEVER'

    def sessionFactory

    def filters = {
        logHibernateStats(controller: '*', action: '*') {
            before = {
                String enabledString = grailsApplication.config.logHibernateStats?.enabled
                Statistics stats = sessionFactory.statistics

                if (enabledString == ALWAYS || (enabledString == ALLOWED && params._logHibernateStats)) {
                    log.debug "\n### Start logging for action: ${controllerName}/${actionName} ###"

                    if (!stats.statisticsEnabled) {
                        stats.statisticsEnabled = true
                    }

                    request[START_TIME] = System.currentTimeMillis()

                }  else if (enabledString == ALLOWED && stats.statisticsEnabled) {
                    stats.statisticsEnabled = false // We assume no one else is using stats
                }
            }

            afterView = {
                String enabledString = grailsApplication.config.logHibernateStats?.enabled

                if (enabledString == ALWAYS || (enabledString == ALLOWED && params._logHibernateStats)) {
                    Statistics stats = sessionFactory.statistics

                    if (!stats.statisticsEnabled) {
                        log.error "Hibernate statistics were disabled during the action: /${controllerName}/${actionName} and could not be logged."

                        return
                    }

                    Long end = System.currentTimeMillis()
                    Long start = request[START_TIME]

                    log.info """
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
    }
}
