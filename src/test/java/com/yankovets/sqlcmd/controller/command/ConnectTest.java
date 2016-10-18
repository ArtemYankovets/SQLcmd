package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ConnectTest {

    private View view;
    private Command command;
    private DatabaseManager manager;
    private String COMMAND_CONNECT_SAMPLE = "connect|sqlcmd|postgres|root";

    @Before
    public void setUp() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(manager, view);
    }

    @Test
    public void testCanProcessWithParameters() {
        // when
        boolean canProcess = command.canProcess(COMMAND_CONNECT_SAMPLE);
        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCorrectConnectCommand() {
        //when
        command.process(COMMAND_CONNECT_SAMPLE);

        //then
        verify(view).write("Success! Got connection for database: sqlcmd, user: postgres.");
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan4() {
        // when
        try {
            command.process("connect|sqlcmd|postgres");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("The amount of arguments for this command, which split by '|' are 3, but expected 4", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThen4() {
        // when
        try {
            command.process("connect|sqlcmd|postgres|root|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("The amount of arguments for this command, which split by '|' are 5, but expected 4", e.getMessage());
        }
    }

    @Test
    public void testConnectCommandWithWrongArguments() throws SQLException {

        // when
        when(manager.isConnected()).thenReturn(false);
        doThrow(new SQLException()).when(manager).connect("sqlcmd", "postgres", "qwe");
        command.process("connect|sqlcmd|postgres|qwe");

        // then
        shouldPrint("[Cant get connection for database:sqlcmd, user:postgres because null.]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
