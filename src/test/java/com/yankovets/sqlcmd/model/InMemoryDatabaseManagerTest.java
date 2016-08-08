package com.yankovets.sqlcmd.model;

public class InMemoryDatabaseManagerTest extends DatabaseManagerTest {

    public DatabaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }
}
