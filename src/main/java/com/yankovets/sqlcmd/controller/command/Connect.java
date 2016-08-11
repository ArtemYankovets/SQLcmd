package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public class Connect implements Command {
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
        try {
            String[] data = command.split("[|]");
            if (data.length != 4){ // TODO 4 - magic number!
                throw new IllegalArgumentException(String.format("The amount of arguments for this command," +
                        " which split by '|' are %s, but expected 4", data.length));
            }

            String databese = data[1];
            String username = data[2];
            String password = data[3];

            manager.connect(databese, username, password);
            view.write("Success!");
        } catch (Exception e) {
            printError(e);
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (e.getCause() != null) {
            message += " " + cause.getMessage();
        }
        view.write("Fail! The cause of: " + message);
        view.write("Try again!\n");
    }
}
