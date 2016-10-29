package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateEntryTest {

    private View view;
    private Command command;
    private DatabaseManager manager;

    @Before
    public void setUp() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new GetTableNames(manager, view);
    }

    @Test
    public void testCanProcessCreateString() {
        // when
        boolean canProcess = command.canProcess("createEntry|");

        // then
//        assertTrue(canProcess);

    }

    @Test
    public void testCanProcessQweString() {
        // when
        boolean canProcess = command.canProcess("qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcess_CreateCommand() throws SQLException {
        // given
        when(manager.getTablesNames().toArray()).thenReturn(new String[]{"users","test"});

        // when
        command.process("createEntry|");

        // then
        shouldPrint("[[users, test]]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
