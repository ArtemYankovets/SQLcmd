package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public class IsConnected implements Command {
    private final DatabaseManager manager;
    private final View view;

    public IsConnected(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("You can't use the command '%s' until you connect to database" +
                " with command 'connect|databaseName|username|password'" , command));
    }

    @Override
    public String getCommandTemplate() {
        return null;
    }

    @Override
    public String getCommandDescription() {
        return null;
    }
}
