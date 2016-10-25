package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Set;

public class ClearTable extends AbstractCommandImpl {
    public ClearTable(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "clear|tableName";
    }

    @Override
    public String getCommandDescription() {
        return "for clearing all table";
    }

    @Override
    public void process(String command) {
        validator.validate();
        String tableName = validator.getParametersOfCommandLine()[1];
        try {
            if (checkExistenceOfTableInDB(tableName)) {
                view.write(String.format("Attention! You are going to delete all data from the table '%s'. Are you sure? " +
                        "[ Y / N ]", tableName));
                String result = view.read().toUpperCase();
                if (result.equals("Y")) {
                    manager.clear(tableName);
                    view.write(String.format("Table '%s' was successfully cleared", tableName));
                }
            }
        } catch (SQLException e) {
            view.write(String.format("Error with clearing table '%s' because: %s", tableName, e.getMessage()));
        }
    }

    private boolean checkExistenceOfTableInDB(String tableName) throws SQLException {
        Set<String> setOfTableNames = manager.getTablesNames();
        if (!setOfTableNames.contains(tableName)) {
            view.write(String.format("There is not the table with name '%s' in database.", tableName));
            return false;
        } else {
            return true;
        }
    }
}
