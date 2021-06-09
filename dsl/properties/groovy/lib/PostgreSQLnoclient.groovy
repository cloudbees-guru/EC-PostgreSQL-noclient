import com.cloudbees.cd.plugin.postgresql.PostgreSQLConnection
import com.cloudbees.flowpdf.*

/**
* PostgreSQLnoclient
*/
class PostgreSQLnoclient extends FlowPlugin {

    @Override
    Map<String, Object> pluginInfo() {
        return [
                pluginName     : '@PLUGIN_KEY@',
                pluginVersion  : '@PLUGIN_VERSION@',
                configFields   : ['config'],
                configLocations: ['ec_plugin_cfgs'],
                defaultConfigValues: [:]
        ]
    }
/** This is a special method for checking connection during configuration creation
    */
    def checkConnection(StepParameters p, StepResult sr) {
        // Use this pre-defined method to check connection parameters
        try {
            // Put some checks here
            def config = context.configValues
            log.info(config)
            // Getting parameters:
            // log.info config.asMap.get('config')
            // log.info config.asMap.get('desc')
            
            // assert config.getRequiredCredential("credential").secretValue == "secret"
        }  catch (Throwable e) {
            // Set this property to show the error in the UI
            sr.setOutcomeProperty("/myJob/configError", e.message + System.lineSeparator() + "Please change the code of checkConnection method to incorporate your own connection checking logic")
            sr.apply()
            throw e
        }
    }
// === check connection ends ===
/**
    * executeSQLCommands - Execute SQL commands/Execute SQL commands
    * Add your code into this method and it will be called when the step runs
    * @param server (required: true)
    * @param port (required: true)
    * @param credential (required: true)
    * @param database (required: true)
    * @param sql_query (required: true)
    
    */
    def executeSQLCommands(StepParameters p, StepResult sr) {
        // Use this parameters wrapper for convenient access to your parameters
        ExecuteSQLCommandsParameters sp = ExecuteSQLCommandsParameters.initParameters(p)

        PostgreSQLConnection postgresql = new PostgreSQLConnection()
        postgresql.setServer(p.asMap.get('server').toString())
        postgresql.setPort(p.asMap.get('port').toString())
        postgresql.setDatabaseName(p.asMap.get('database').toString())
        postgresql.setLogin(((Credential)p.asMap.get('credential')).getUserName())
        postgresql.setPassword(((Credential)p.asMap.get('credential')).getSecretValue())

        log.info "Executing query:"
        postgresql.executeQuery(p.asMap.get('sql_query').toString())
        log.info "Query was executed"

        sr.apply()
    }

// === step ends ===

}