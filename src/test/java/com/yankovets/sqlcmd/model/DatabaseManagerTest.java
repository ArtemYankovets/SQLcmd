package com.yankovets.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup() throws SQLException{
        manager = getDatabaseManager();
        manager.connect("sqlcmd", "localhost", "5433", "postgres", "root");
        manager.clear("users");
    }

    public abstract DatabaseManager getDatabaseManager();

    @Test
    public void tetsGetAllTableNames() throws SQLException {
        Set<String> tableNames = manager.getTablesNames();
        assertEquals("[test, users]", Arrays.toString(tableNames.toArray()));
    }

    @Test
    public void testGetTableData() throws SQLException {
        // given
        manager.clear("users");

        // when
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.createEntry("users", input);

        // then
        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, pass, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() throws SQLException {
        // given
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.createEntry("users", input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        manager.update("users", 13, newValue);

        // then
        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, pass2, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testGetColumnNames() throws SQLException {
        // given
        manager.clear("users");

        // when
        Set<String> columnNames = manager.getTableColumns("users");

        // then
        assertEquals("[name, password, id]", columnNames.toString());
    }

    @Test
    public void testIsConnected() {
        // then
        assertTrue(manager.isConnected());
    }


}