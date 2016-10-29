package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HelpTest {

    private View view;
    private Command command;

    @Before
    public void setUp() {
        view = mock(View.class);
        command = new Help(view);
    }

    @Test
    public void testCanProcessHelpString() {
        // when
        boolean canProcess = command.canProcess("help");

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
    public void testProcess_HelpCommand() {

        // when
        command.process("help");

        // then
        shouldPrint("[Exist commands:, " +
                    "\tconnect|databaseName|username|password, " +
                    "\t\tfor connection to database, which we planning to work with, " +
                    "\tlist, " +
                    "\t\tfor getting all tables from database, witch you got connection, " +
                    "\tclear|tableName, " +
                    "\t\tfor all table clearing, " +
                    "\tcreateEntry|tableName|column1|value1|column2|value2|...|columnN|valueN, " +
                    "\t\tfor creating notes in database, " +
                    "\tfind|tableName, " +
                    "\t\tfor getting the data from table 'tableName', " +
                    "\thelp, " +
                    "\t\tfor outputing this list of commend to screen, " +
                    "\texit, " +
                    "\t\tfor exit from application]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
