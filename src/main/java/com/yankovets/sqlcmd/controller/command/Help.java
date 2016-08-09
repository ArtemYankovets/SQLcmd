package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("Exist commands:");
        view.write("\tlist");
        view.write("\t\tfor getting all tables from database, witch you got connection");

        view.write("\tfind|tableName");
        view.write("\t\tfor getting the data from table 'tableName'");

        view.write("\thelp");
        view.write("\t\tfor outputing this list of commend to screen");

        view.write("\texit");
        view.write("\t\tfor exit from application");
    }
}
