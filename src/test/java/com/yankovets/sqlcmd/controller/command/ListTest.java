package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ListTest {

    private View view;
    private Command command;
    private DatabaseManager manager;

    @Before
    public void setUp() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new List(manager, view);
    }

    @Test
    public void testCanProcessHelpString() {
        // when
        boolean canProcess = command.canProcess("list");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessQweString() {
        // when
        boolean canProcess = command.canProcess("qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcess_ListCommand() {
        // given
        when(manager.getTableNames()).thenReturn(new String[]{"users","test"});

        // when
        command.process("list");

        // then
        shouldPrint("[[users, test]]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
