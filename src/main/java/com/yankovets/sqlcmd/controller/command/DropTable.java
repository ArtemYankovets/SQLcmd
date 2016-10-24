package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

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
            manager.dropTable(tableName);
            view.write(String.format("The table '%s' was successfully deleted from database", tableName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
