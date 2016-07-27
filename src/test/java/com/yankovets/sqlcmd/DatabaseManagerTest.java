package com.yankovets.sqlcmd;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = new DatabaseManager();
        manager.connect("sqlcmd", "postgres", "root");
    }

    @Test
    public void tetsGetAllTableNames() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[users, test]", Arrays.toString(tableNames));
    }

}