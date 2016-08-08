package com.yankovets.sqlcmd.view;

import java.util.Scanner;

public class Console implements View {
    public void write(String message) {
        System.out.println(message);
    }

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
