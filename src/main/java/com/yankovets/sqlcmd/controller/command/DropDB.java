package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class DropDB implements Command {

    private final DatabaseManager manager;
    private final View view;

    public DropDB(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropDB|");
    }

    @Override
    public void process(String command) {
        String[] databaseName = command.split("[|]");
        if (databaseName.length != 2) {
            throw new IllegalArgumentException("Формат команды 'dropDB|databaseName', а ты ввел: " + command);
        }

        try {
            manager.dropDB(databaseName[1]);
            view.write("База '" + databaseName[1] + "' удалена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "\tdropBD|databaseName\r\n" +
               "\t\tfor deletion database\r\n";
    }
}
