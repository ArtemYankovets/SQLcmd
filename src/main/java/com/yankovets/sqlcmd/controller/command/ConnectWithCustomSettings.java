package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;


public class ConnectWithCustomSettings {
  //private static String COMMAND_CONNECT_SAMPLE_1 = "connect|dbName|userName|password";
    private static String COMMAND_CONNECT_SAMPLE_1 = "connect|sqlcmd|postgres|root";

  //private static String COMMAND_CONNECT_SAMPLE_2 = "connect|driverName        |host     :port|dbName|userName|password";
  //private static String COMMAND_CONNECT_SAMPLE_3 = "connect|driverName        |hostName      |dbName|userName|password";
  //private static String COMMAND_CONNECT_SAMPLE_3 = "connect|jdbc:postgresql://|localhost:5432|sqlcmd|postgres|root";

    private DatabaseManager manager;
    private View view;

    public ConnectWithCustomSettings(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }



    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }


    public void process(String command) {
  /*      String[] data = command.split("[|]");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The amount of arguments for this command," +
                            " which split by '|' are %s, but expected %s",
                    data.length, count()));
        }

        String database = data[1];
        String userName = data[2];
        String password = data[3];

        try {
            manager.connect(database, userName, password);
            view.write(String.format("Success! Got connection for database: %s, user: %s.", database, userName));
        } catch (SQLException e) {
            view.write(String.format("Cant get connection for database:%s, user:%s because %s.",
                    database, userName,
                    e.getMessage()));
        }*/
    }


    public String getCommandDescription() {
        return null;
    }


    private int count() {
        return COMMAND_CONNECT_SAMPLE_1.split("[|]").length;
    }

}
