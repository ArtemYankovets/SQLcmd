package com.yankovets.sqlcmd.controller;

import com.yankovets.sqlcmd.controller.command.*;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import sun.plugin2.main.client.DisconnectedExecutionContext;

public class MainController {

    private Command[] commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(manager, view),
                new CreateDB(manager, view),
                new GetDBNames(manager, view),
                new DropDB(manager, view),
                new Help(view),
                new Exit(view),
                new Disconnect(manager, view),
                new IsConnected(manager, view),
                new CreateTable(manager, view),
                new GetTableNames(manager, view),
                new DropTable(manager, view),
                new GetDBNames(manager, view),
                new CreateEntry(manager, view),
                new ClearTable(manager, view),
                new GetTableData(manager, view),
                new Unsupported(view)
        };
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            // do nothing
        }

    }

    private void doWork() {
        view.write("Welcome!");
        view.write("Please input database name, user name and password in format: connect|database|userName|password");

        while (true) {
            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                       throw e;
                    }
                    printError(e);
                    break;
                }
            }
            view.write("Input command (or 'help' for help):");
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (e.getCause() != null) {
            message += " " + cause.getMessage();
        }
        view.write("Fail! The cause of: " + message);
        view.write("Try again!");
    }
}
