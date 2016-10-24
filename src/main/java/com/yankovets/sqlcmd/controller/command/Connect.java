package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class Connect extends AbstractCommandImpl {

    private String commandConnectSample = "connect|sqlcmd|postgres|root";
    private String host = "localhost";
    private String port = "5433";

    public Connect(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "connect|databaseName|userName|password|host[optional]|port[optional]";
    }

    @Override
    public String getCommandDescription() {
        return "for connection to database, which we are planning to work with";
    }


    @Override
    public void process(String command) {
        validator.checkNumberOfParameters();
        String[] parameters = validator.getParametersOfCommandLine();
        String databaseName = parameters[1];
        String userName = parameters[2];
        String password = parameters[3];
        String host = this.host;
        String port = this.port;
        if ((parameters.length == 6) && (parameters[4] != null) && (parameters[5] != null)) {
            host = parameters[4];
            port = parameters[5];
        }

        try {
            manager.connect(databaseName, host, port, userName, password);
            view.write(String.format("Success! Got connection for database: %s, user: %s.",
                    databaseName, userName));
        } catch (SQLException e) {
            view.write(String.format("Cant get connection for database:%s, user:%s because %s.",
                    databaseName, userName,
                    e.getMessage()));
        }
    }
}
