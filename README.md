logHibernateStats
=================

### Summary
A simple Grails 3 plugin to log Hibernate statistics on controller actions.

For the Grails 2 plugin code and documentation, see: https://github.com/ishults/log-hibernate-stats/tree/grails_2.x

### Configuration and Usage
To add this plugin, in your `build.gradle` add the repository:

    maven { url "http://dl.bintray.com/ishults/plugins" }

and add the dependency:

    compile "com.ishults:log-hibernate-stats:1.0.17"


Then just update your config (such as `application.yml`) to add
```
logHibernateStats:
    enabled: 'ALWAYS'// From ALWAYS, ALLOWED, NEVER
```
for the environments you want to track statistics for. 

Then in your `logback.groovy` set:
```
logger 'grails.app.controllers.com.ishults.logHibernateStats.LogHibernateStatsInterceptor',
    DEBUG, ['STDOUT'], false // Or INFO
```
You should now be seeing output like:
```
INFO  controller.ControllerFilters  -
############## Hibernate Stats ##############
Action:                     /controller/actionName

Transaction Count:          2
Flush Count:                1
Prepared Statement Count:   2

Total time:                 500 ms
#############################################
```
after each request.  If you set the logging to 'debug', you will also see:
```
DEBUG  controller.ControllerFilters  -
### Start logging for action: controller/actionName ###
```
at the start of each action (useful if logSql is enabled too).

If instead you'd like to target only specific actions, you can set
```
logHibernateStats.enabled = 'ALLOWED'
```
and instead append the parameter '_logHibernateStats=true' to your request.  This will isolate the logging to just that request.

It is recommended to keep the plugin enabled value at 'NEVER' by default, and setting it to 'ALLOWED' or 'ALWAYS' when debugging in development.

### Caveats
* This plugin heavily assumes that no other place in the code is clearing/using Hibernate statistics.
* If there are background services interacting with Hibernate, or if multiple requests are sent in succession, the statistics may not be accurate.

### Credits
Plugin created by Igor Shults.

Official Grails 3.x plugin page here: https://bintray.com/ishults/plugins/com.ishults%3Alog-hibernate-stats/view

Official Grails 2.x plugin page here: http://grails.org/plugin/log-hibernate-stats

If you're not interested in running this as a plugin, I wrote a blog post on some standalone code here: http://www.objectpartners.com/2014/04/22/tracking-hibernate-statistics-across-grails-actions/

Inspired by a post on Hibernate logging by Himanshu Seth: http://www.intelligrape.com/blog/2011/11/07/grails-find-number-of-queries-executed-for-a-particular-request/

