package com.yankovets.sqlcmd.model;

public class JDBCDatabaseManagerTest extends DatabaseManagerTest {

    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }
}
