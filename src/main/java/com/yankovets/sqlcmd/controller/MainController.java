package com.yankovets.sqlcmd.controller;

import com.yankovets.sqlcmd.model.DataSet;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.util.Arrays;

public class MainController {

    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void run() {
        connectToDb(); // sqlcmd|postgres|root
        while (true) {
            view.write("Input command (or 'help' for help):");
            String command = view.read();
            if (command.equals("list")) {
                doList();
            } else if (command.startsWith("find|")) {
                doFind(command);
            } else if (command.equals("help")) {
                doHelp();
            } else if (command.equals("exit")) {
                view.write("See you next time!");
                System.exit(0);
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

    private void doHelp() {
        view.write("Exist commands:");
        view.write("\tlist");
        view.write("\t\tfor getting all tables from database, witch you got connection");

        view.write("\tfind|tableName");
        view.write("\t\tfor getting the data from table 'tableName'");

        view.write("\thelp");
        view.write("\t\tfor outputing this list of commend to screen");

        view.write("\texit");
        view.write("\t\tfor exit from application");
    }

    private void doList() {
        String[] tableNames = manager.getTableNames();
        String message = Arrays.toString(tableNames);
        view.write(message);
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
