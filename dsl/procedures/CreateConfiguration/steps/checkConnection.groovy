$[/myProject/groovy/scripts/preamble.groovy.ignore]

PostgreSQLnoclient plugin = new PostgreSQLnoclient()
plugin.runStep('$[/myProcedure/name]', 'checkConnection', 'checkConnection')