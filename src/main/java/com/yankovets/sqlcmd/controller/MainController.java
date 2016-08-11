package com.yankovets.sqlcmd.controller;

import com.yankovets.sqlcmd.controller.command.*;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public class MainController {

    private Command[] commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new List(manager, view),
                new Find(manager, view),
                new Unsupported(view)
        };
    }

    public void run() {
        view.write("Hello, user!");
        view.write("Please input database name, user name and password in format: connect|database|userName|password");

        while (true) {
            String commandName = view.read();
            for (Command command: commands){
                if (command.canProcess(commandName)) {
                    command.process(commandName);
                    break;
                }
            }
            view.write("Input command (or 'help' for help):");
        }
    }



}
