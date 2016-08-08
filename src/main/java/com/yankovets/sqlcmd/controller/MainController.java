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

    public void run(){
        connectToDb();
    }

    private void connectToDb() {
        view.write("Hello, user!");
        view.write("Write, please input database name, user name and password in format: database|userName|password");

        while (true) {
            String string = view.read();
            String[] data = string.split("[|]");
            String databese = data[0];
            String username = data[1];
            String password = data[2];
            try {
                manager.connect(databese, username, password);
                break;
            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getCause() != null) {
                    message += " " + e.getCause().getMessage();
                }
                view.write("Fail! The because of: " + message);
                view.write("Try again!\n");
            }
        }
        view.write("Success!");
    }
}
