package com.yankovets.sqlcmd.model;

import java.util.Arrays;

public class InMemoryDatabaseManager implements DatabaseManager {

    public static final String TABLE_NAME = "users"; // TODO implement multitables

    private DataSet[] data = new DataSet[1000];
    private int freeIndex = 0;

    @Override
    public DataSet[] getTableData(String tableName) {
        validateTable(tableName);
        return Arrays.copyOf(data, freeIndex);
    }

    private void validateTable(String tableName) {
        if (!"users".equals(TABLE_NAME)){
            throw new IllegalArgumentException("Only for 'users' table, tut you try to work with:" + tableName);
        }
    }

    @Override
    public String[] getTableNames() {
        return new String[] { TABLE_NAME };
    }

    @Override
    public void connect(String database, String userName, String password) {
        // do nothing
    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
        data = new DataSet[1000];
        freeIndex = 0;
    }

    @Override
    public void create(String tableName, DataSet input) {
        validateTable(tableName);
        data[freeIndex++] = input;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < freeIndex; index++) {
            if (data[index].get("id") instanceof Integer) {
                if ((Integer)data[index].get("id") == id) {
                    data[index].updateFrom(newValue);
                }
            }
        }
    }

    @Override
    public String[] getTableColumns(String tableName) {
        return new String[] {"name", "password", "id"};
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
