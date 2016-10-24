package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class CreateTable extends AbstractCommandImpl {

    public CreateTable(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "createTable|tableName";
    }

    @Override
    public String getCommandDescription() {
        return "for creating table";
    }

    @Override
    public void process(String command) {
        validator.validate();
        String tableName = validator.getParametersOfCommandLine()[1];
        try {
            manager.createTable(tableName);
            view.write(String.format("New table '%s' was successfully created", tableName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
