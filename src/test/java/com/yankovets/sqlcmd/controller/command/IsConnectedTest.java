package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IsConnectedTest {

    private View view;
    private Command command;
    private DatabaseManager manager;

    @Before
    public void setUp() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new IsConnected(manager, view);
    }

    @Test
    public void testProcess() {
        command.process("some command which need to get connection before working with DB");
        verify(view).write("You can't use the command 'some command which need to get connection before working with DB' until you connect to database with command 'connect|databaseName|username|password'"
        );
    }

    @Test
    public void testCanProcess() {
        // when
        boolean canProcess = command.canProcess("connect|");
        //then
        assertTrue(canProcess);
    }



    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
