package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;

public class Exit extends AbstractCommandImpl {

    public Exit(View view) {
        super(view);
    }

    @Override
    public String getCommandTemplate() {
        return "exit";
    }

    @Override
    public String getCommandDescription() {
        return "for exit from application";
    }

    @Override
    public void process(String command) {
        validator.validate();
        view.write("See you next time!");
        throw new ExitException();
    }


}
