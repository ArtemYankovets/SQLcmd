package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UnsupportedTest {

    private View view;
    private Command command;

    @Before
    public void setUp() {
        view = mock(View.class);
        command = new Unsupported(view);
    }

    @Test
    public void testCanProcessUnsupportedString() {
        // when
        boolean canProcess = command.canProcess(anyString());

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessNotExist() {
        // when
        command.process("some wrong command");

        // then
        verify(view).write("Non exist command: 'some wrong command'");
    }
}
