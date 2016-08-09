package com.yankovets.sqlcmd.controller;

import com.yankovets.sqlcmd.controller.command.*;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public class MainController {

    private Command[] commands;
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[] {new Exit(view), new Help(view), new List(manager, view), new Find(manager, view)};
    }

    public void run() {
        connectToDb();
        while (true) {
            view.write("Input command (or 'help' for help):");
            String command = view.read();
            if (commands[2].canProcess(command)) {
                commands[2].process(command);
            } else if (commands[3].canProcess(command)) {
                commands[3].process(command);
            } else if (commands[1].canProcess(command)) {
                commands[1].process(command);
            } else if (commands[0].canProcess(command)) {
                commands[0].process(command);
            } else {
                view.write("Non exist command: " + command);
            }
            //
            //
            //
            //
        }
    }


    private void connectToDb() {
        view.write("Hello, user!");
        view.write("Write, please input database name, user name and password in format: database|userName|password");

        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("[|]");

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
        String message = /*e.getClass().getSimpleName() + ": "+ */e.getMessage();
        Throwable cause = e.getCause();
        if (e.getCause() != null) {
            message += " " + /*cause.getClass().getSimpleName() + ": " +*/ cause.getMessage();
        }
        view.write("Fail! The because of: " + message);
        view.write("Try again!\n");
    }
}
