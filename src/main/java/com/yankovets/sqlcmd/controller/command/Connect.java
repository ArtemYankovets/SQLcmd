package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public class Connect implements Command {

    private static String COMMAND_CONNECT_SAMPLE = "connect|sqlcmd|postgres|root";

    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }


    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("[|]");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The amount of arguments for this command," +
                            " which split by '|' are %s, but expected %s",
                    data.length, count()));
        }

        String databese = data[1];
        String username = data[2];
        String password = data[3];

        manager.connect(databese, username, password);
        view.write("Success!");
    }

    private int count() {
        return COMMAND_CONNECT_SAMPLE.split("[|]").length;
    }

}
