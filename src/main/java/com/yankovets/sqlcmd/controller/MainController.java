package com.yankovets.sqlcmd.controller;

import com.yankovets.sqlcmd.controller.command.Command;
import com.yankovets.sqlcmd.controller.command.Exit;
import com.yankovets.sqlcmd.controller.command.Help;
import com.yankovets.sqlcmd.controller.command.List;
import com.yankovets.sqlcmd.model.DataSet;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public class MainController {

    private Command[] commands;
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[] {new Exit(view), new Help(view), new List(manager, view)};
    }

    public void run() {
        connectToDb();
        while (true) {
            view.write("Input command (or 'help' for help):");
            String command = view.read();
            if (commands[2].canProcess(command)) {
                commands[2].process(command);
            } else if (command.startsWith("find|")) {
                doFind(command);
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

    private void doFind(String command) {
        String[] data = command.split("[|]");
        String tableName = data[1];

        DataSet[] tableData = manager.getTableData(tableName);
        String[] tableColumns = manager.getTableColumns(tableName);

        printHeader(tableColumns);
        printTable(tableData);
    }

    private void printTable(DataSet[] tableData) {
        for (DataSet row: tableData){
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("-------------------------");
        view.write(result);
        view.write("-------------------------");
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
