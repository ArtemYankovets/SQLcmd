package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;

public class FakeView implements View {

    private String messages = "";
    private String input = null;

    @Override
    public void write(String message) {
        messages += message;
    }

    @Override
    public String read() {
        if (this.input == null){
            throw new IllegalStateException("You should initialised method read()");
        }

        String result = input;
        this.input = null;
        return result;
    }

    public void addRead (String input) {
        this.input = input;
    }

    public String getContent() {
        return messages;
    }
}
