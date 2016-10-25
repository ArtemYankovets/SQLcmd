package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

public class GetTableNames extends AbstractCommandImpl {


    public GetTableNames(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "tables";
    }

    @Override
    public String getCommandDescription() {
        return "for getting all tables from database, witch you got connection";
    }

    @Override
    public void process(String command) {
        validator.validate();
        Set<String> tableNames = null;
        try {
            tableNames = manager.getTablesNames();
            view.write(Arrays.toString(tableNames.toArray()));
        } catch (SQLException e) {
            view.write(String.format("Error with showing", tableNames, e.getMessage()));
        }
    }
}
