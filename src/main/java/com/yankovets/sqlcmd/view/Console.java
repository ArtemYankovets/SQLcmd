package com.yankovets.sqlcmd.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {
    public void write(String message) {
        System.out.println(message);
    }

    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
