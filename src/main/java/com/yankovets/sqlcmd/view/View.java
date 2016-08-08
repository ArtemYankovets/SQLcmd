package com.yankovets.sqlcmd.view;

public interface View {
    void write(String message);

    String read();
}
