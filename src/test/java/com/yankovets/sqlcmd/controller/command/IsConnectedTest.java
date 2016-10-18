package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

}
