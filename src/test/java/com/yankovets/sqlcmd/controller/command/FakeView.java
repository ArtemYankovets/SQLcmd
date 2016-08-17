package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;

public class FakeView implements View {

    private String messages = "";

    @Override
    public void write(String message) {
        messages += message;
    }

    @Override
    public String read() {
        return null;
    }

    public String getContent() {
        return messages;
    }
}
