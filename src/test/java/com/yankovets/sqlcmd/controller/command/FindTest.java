package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DataSet;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FindTest {

    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
    }

    @Test
    public void testPrintTableData() {
        // given
        Command command = new Find(manager, view);
        when(manager.getTableColumns("users")).
                thenReturn(new String[]{"id", "name", "password"});

        DataSet user1 = new DataSet();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");

        DataSet user2 = new DataSet();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");

        DataSet[] data = new DataSet[]{user1, user2};

        when(manager.getTableData("users")).
                thenReturn(data);

        // when
        command.process("find|users");

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[-------------------------, " +
                "|id|name|password|, " +
                "-------------------------, " +
                "|12|Stiven|*****|, " +
                "|13|Eva|+++++|, " +
                "-------------------------]", captor.getAllValues().toString());
    }

    @Test
    public void testPrintEmptyTableData() {
        // given
        Command command = new Find(manager, view);
        when(manager.getTableColumns("users")).
                thenReturn(new String[]{"id", "name", "password"});

        DataSet[] data = new DataSet[0];

        when(manager.getTableData("users")).
                thenReturn(data);

        // when
        command.process("find|users");

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[-------------------------, " +
                "|id|name|password|, " +
                "-------------------------, " +
                "-------------------------]", captor.getAllValues().toString());
    }

    @Test
    public void testCanProcessFindWithParametersString() {

        // given
        Command command = new Find(manager, view);

        // when
        boolean canProcess = command.canProcess("find|users");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessFindWithoutParametersString() {
        // given
        Command command = new Find(manager, view);

        // when
        boolean canProcess = command.canProcess("find");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessQweString() {
        // given
        Command command = new Find(manager, view);

        // when
        boolean canProcess = command.canProcess("qwe|users");

        // then
        assertFalse(canProcess);
    }
}
