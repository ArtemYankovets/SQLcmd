package com.yankovets.sqlcmd.controller;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.model.JDBCDatabaseManager;
import com.yankovets.sqlcmd.view.Console;
import com.yankovets.sqlcmd.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();        // connect|sqlcmd|postgres|root
        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
