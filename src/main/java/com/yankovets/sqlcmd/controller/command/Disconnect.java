package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class Disconnect extends AbstractCommandImpl {

    public Disconnect(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "disconnect";
    }

    @Override
    public String getCommandDescription() {
        return "for disconnection from current database";
    }

    @Override
    public void process(String command) {
        validator.validate();
        try {
            manager.disconnectFromDB();
            view.write("Disconnected");
        } catch (SQLException e) {
            view.write(String.format("Error with disconnection", e.getMessage()));
        }
    }
}
