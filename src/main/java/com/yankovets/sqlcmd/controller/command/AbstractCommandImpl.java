package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.controller.command.utility.CommandValidation;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

public abstract class AbstractCommandImpl implements Command {
    protected DatabaseManager manager;
    protected View view;
    protected CommandValidation validator;

    public AbstractCommandImpl(View view) {
        this.view = view;
    }

    public AbstractCommandImpl (DatabaseManager databaseManager, View view) {
        this.manager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        validator = new CommandValidation(command, getCommandTemplate());
        String[] paramOfCommandTemplate = validator.splitCommandUpOnParameters(getCommandTemplate());
        String commandLineStartWith = validator.getParametersOfCommandLine()[0];
        return paramOfCommandTemplate[0].equals(commandLineStartWith);
    }
}
