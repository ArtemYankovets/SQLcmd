package com.yankovets.sqlcmd.controller.command;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.*;

public class ExitTest {

    private FakeView view = new FakeView();

    @Test
    public void testCanProcessExitString() {

        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("exit");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessQweString() {
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_throwsExitExeption() {
        // given
        Command command = new Exit(view);

        // when
        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }

        // then
        assertEquals("See you next time!", view.getContent());
        // throws ExitExeption
    }
}
