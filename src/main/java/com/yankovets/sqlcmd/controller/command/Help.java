package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Help extends AbstractCommandImpl {

    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_RESET = "\u001B[0m";
    private List<Command> commands;

    public Help(View view) {
        super(view);
        this.commands = new ArrayList<>(Arrays.asList(
                new Connect(manager, view),
                new GetDBNames(manager, view),
                new GetTableNames(manager, view),
                new CreateDB(manager, view),
//                new DropDB(manager, view),
                new CreateTable(manager, view),
                new GetTableData(manager, view),
                new DropTable(manager, view),
                new CreateEntry(manager, view),
                new ClearTable(manager, view),
                this,
                new Exit(view)
        ));
    }

    @Override
    public String getCommandTemplate() {
        return "help";
    }

    @Override
    public String getCommandDescription() {
        return "for showing a list of existing commands";
    }

    @Override
    public void process(String command) {
        view.write("Exist commands:");
        for (Command item : commands) {
            view.write(ANSI_BLUE + "\t" + item.getCommandTemplate());
            view.write(ANSI_RESET + "\t\t" + item.getCommandDescription());
        }
    }
}
