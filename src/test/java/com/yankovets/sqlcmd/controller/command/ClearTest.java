package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void testClearTable() {
        // given

        // when
        command.process("clear|users");

        // then
        verify(manager).clear("users");
        verify(view).write("Table users was successfully cleared");
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
}
