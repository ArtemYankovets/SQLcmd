package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExitWithMockitoTest {

    private View view;

    @Before
    public void setUp () {
        view = mock(View.class);
    }

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
        verify(view).write("See you next time!");
    }
}
