package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class CreateDB extends AbstractCommandImpl {

    public CreateDB(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "createDB|databaseName";
    }

    @Override
    public String getCommandDescription() {
        return "for creating database";
    }

    @Override
    public void process(String command) {
        validator.validate();
        String databaseName = validator.getParametersOfCommandLine()[1];
        try {
            manager.createDB(databaseName);
            view.write(String.format("New database '%s' was successfully created", databaseName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
