package com.yankovets.sqlcmd.model;

public class JDBCDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }
}
