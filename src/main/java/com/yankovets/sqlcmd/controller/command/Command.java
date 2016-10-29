package com.yankovets.sqlcmd.controller.command;

public interface Command {

    String getCommandTemplate();

    String getCommandDescription();

    boolean canProcess(String command);

    void process(String command);
}
