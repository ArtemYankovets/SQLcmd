package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setUp() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void testClearTableWithTrueValidationWithConfirm() {
        // when
        String table = "users";
        Set<String> tableNames = new HashSet<>();
        tableNames.add(table);
        when(manager.getTableNames()).thenReturn(tableNames);
        when(view.read()).thenReturn("Y");
        command.process("clear|" + table);

        // then
        shouldPrint("[Attention! You are going to delete all data from the table 'users'. Are you sure? [ Y / N ]" +
                ", Table users was successfully cleared]");
        verify(manager).clear("users");
    }

    @Test
    public void testClearTableWithTrueValidationWithOutConfirm() {
        // when
        String table = "users";
        Set<String> tableNames = new HashSet<>();
        tableNames.add(table);
        when(manager.getTableNames()).thenReturn(tableNames);
        when(view.read()).thenReturn("N");
        command.process("clear|" + table);

        // then
        shouldPrint("[Attention! You are going to delete all data from the table 'users'. Are you sure? [ Y / N ]]");
        verify(manager, never()).clear("users");
    }



    @Test
    public void testClearTableWithFalseValidation() {
        // when
        when(manager.getTableNames()).thenReturn(new HashSet<String>());
        when(view.read()).thenReturn("N");
        command.process("clear|qwe");

        // then
        verify(view).write("There is not the table with name 'qwe' in database.");
    }

    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("clear|users");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("clear");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessQweString() {
        // when
        boolean canProcess = command.canProcess("qwe|users");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThen2() {
        // when
        try {
            command.process("clear");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Command format 'clear|tableName', but you taped: clear", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThen2() {
        // when
        try {
            command.process("clear|tableName|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Command format 'clear|tableName', but you taped: clear|tableName|qwe", e.getMessage());
        }
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
