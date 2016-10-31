package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Set;

public class DropTable extends AbstractCommandImpl {

    public DropTable(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "dropTable|tableName";
    }

    @Override
    public String getCommandDescription() {
        return "for deletion table from database";
    }

    @Override
    public void process(String command) {
        validator.validate();
        String tableName = validator.getParametersOfCommandLine()[1];

        try {
            if (checkExistenceOfTableInDB(tableName)) {
                view.write(String.format("Attention! You are going to table '%s' from database. Are you sure? " +
                        "[ Y / N ]", tableName));
                String result = view.read().toUpperCase();
                if (result.equals("Y")) {
                    manager.dropTable(tableName);
                    view.write(String.format("The table '%s' was successfully deleted from database", tableName));
                }
            }
        } catch (SQLException e) {
            view.write(String.format("Error with deleting table '%s' because: %s", tableName, e.getMessage()));
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
