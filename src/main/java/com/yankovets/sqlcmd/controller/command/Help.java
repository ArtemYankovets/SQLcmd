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

        view.write("\tconnect|databaseName|username|password");
        view.write("\t\tfor connection to database, which we planning to work with");

        view.write("\tdatabases");
        view.write("\t\tfor getting all databases, witch you got connection");

        view.write("\ttables");
        view.write("\t\tfor getting all tables from database, witch you got connection");

        view.write("\tcreateEntry|tableName|column1|value1|column2|value2|...|columnN|valueN");
        view.write("\t\tfor creating notes in database");

        view.write("\tclear|tableName");
        view.write("\t\tfor all table clearing");

        view.write("\tshow|tableName");
        view.write("\t\tfor getting the data from table 'tableName'");

        view.write("\thelp");
        view.write("\t\tfor outputing this list of commend to screen");

        view.write("\texit");
        view.write("\t\tfor exit from application");
    }

    @Override
    public String getCommandTemplate() {
        return null;
    }

    @Override
    public String getCommandDescription() {
        return "\texit\r\n" +
                "\t\tfor exit from application\r\n";
    }
}
