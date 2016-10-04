package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.util.Set;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;

    public Clear(DatabaseManager databaseManager, View view) {
        this.manager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("[|]");
        if (data.length != 2) {
            throw new IllegalArgumentException("Command format 'clear|tableName', but you taped: " + command);
        }

        String table = data[1];

        if (commandValidation(table)) {
            view.write(String.format("Attention! You are going to delete all data from the table '%s'. Are you sure? " +
                    "[ Y / N ]", table));
            String result = view.read().toUpperCase();
            if (result.equals("Y")) {
                manager.clear(data[1]);
                view.write(String.format("Table %s was successfully cleared", data[1]));
            }
        }
    }

    private boolean commandValidation (String table) {
        Set<String> tableNames = manager.getTableNames();
        if (!tableNames.contains(table)) {
            view.write(String.format("There is not the table with name '%s' in database.", table));
            return false;
        } else {
            return true;
        }
    }

}
