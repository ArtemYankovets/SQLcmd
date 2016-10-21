package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class DropTable implements Command {

    private final DatabaseManager manager;
    private final View view;

    public DropTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropTable|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("[|]");
        if (data.length != 2) {
            throw new IllegalArgumentException("Формат команды 'dropTable|tableName', а ты ввел: " + command);
        }
        try {
            manager.dropTable(data[1]);
            view.write("Tаблица '" + data[1] + "' удалена.");
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
