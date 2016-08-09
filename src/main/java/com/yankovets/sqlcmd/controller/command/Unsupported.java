package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;

public class Unsupported implements Command {
    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("Non exist command: " + command);
    }
}
