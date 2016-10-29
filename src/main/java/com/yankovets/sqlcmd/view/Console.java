package com.yankovets.sqlcmd.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Console implements View {
    public void write(String message) {
        System.out.println(message);
    }

    public String read() {
        Set<String> inputCommandSet = new LinkedHashSet<>();

        try {
            Scanner scanner = new Scanner(System.in);
            String inputCommand = scanner.nextLine();
            return inputCommand;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private void cursor() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {
            public boolean postProcessKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        // get previous string
                        write("UP");
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        // get next string
                        write("DOWN");
                    }
                    // do smth
                }
                return true;
            }
        });
    }
/*
    class KeyEvent implements KeyListener {

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {
            // do nothing
        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (e == KeyEvent.VK_KP_UP)
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
            // do nothing
        }
    }*/
}

