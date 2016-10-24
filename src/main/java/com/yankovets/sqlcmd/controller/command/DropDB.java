package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class DropDB extends AbstractCommandImpl {

    public DropDB(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "dropDB|databaseName";
    }

    @Override
    public String getCommandDescription() {
        return "for deletion database";
    }

    @Override
    public void process(String command) {
        validator.validate();
        String databaseName = validator.getParametersOfCommandLine()[1];
        try {
            manager.dropDB(databaseName);
            view.write(String.format("The database '%s' was successfully deleted", databaseName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
