package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

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
        manager.clear(data[1]);

        view.write(String.format("Table %s was successfully cleared", data[1]));
    }
}
