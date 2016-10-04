package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.util.Arrays;
import java.util.Set;

public class List implements Command {
    private DatabaseManager manager;
    private View view;


    public List(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        Set<String> tableNames = manager.getTableNames();
        view.write(Arrays.toString(tableNames.toArray()));
    }
}
