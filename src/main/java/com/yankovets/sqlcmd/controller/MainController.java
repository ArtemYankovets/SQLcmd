package com.yankovets.sqlcmd.controller;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public class MainController {

    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void run() {
        connectToDb();
    }

    private void connectToDb() {
        view.write("Hello, user!");
        view.write("Write, please input database name, user name and password in format: database|userName|password");

        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("[|]");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Amount of parameters which split by " +
                            "'|' are not correct, expect 3, but exist:" + data.length);
                }
                String databese = data[0];
                String username = data[1];
                String password = data[2];

                manager.connect(databese, username, password);
                break;
            } catch (Exception e) {
                printError(e);
            }
        }
        view.write("Success!");
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.write("Fail! The because of: " + message);
        view.write("Try again!\n");
    }
}
