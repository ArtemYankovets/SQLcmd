package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

public class GetDBNames extends AbstractCommandImpl {


    public GetDBNames(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "databases";
    }

    @Override
    public String getCommandDescription() {
        return "for getting names of all databases";
    }

    @Override
    public void process(String command) {
        validator.validate();
        Set<String> databaseNames = null;
        try {
            databaseNames = manager.getDatabasesNames();
            view.write(Arrays.toString(databaseNames.toArray()));
        } catch (SQLException e) {
            view.write(String.format("Error with showing", databaseNames, e.getMessage()));
        }
    }
}
