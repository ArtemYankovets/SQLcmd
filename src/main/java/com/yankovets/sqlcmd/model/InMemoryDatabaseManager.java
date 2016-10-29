package com.yankovets.sqlcmd.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class InMemoryDatabaseManager implements DatabaseManager {

    public static final String TABLE_NAME = "users"; // TODO implement multitables

    private Set<String> tableNames = new HashSet<>();
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
    public Set<String> getTablesNames() {
        tableNames.add(TABLE_NAME);
        tableNames.add("test");
        return tableNames;
    } // TODO to remove test

    @Override
    public void connect(String database,  String host, String port, String userName, String password) {
        // do nothing
    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
        data = new DataSet[1000];
        freeIndex = 0;
    }

    @Override
    public void createEntry(String tableName, DataSet input) {
        validateTable(tableName);
        data[freeIndex++] = input;
    }

    @Override
    public void createDB(String databaseName) throws SQLException {

    }

    @Override
    public void createTable(String tableName) throws SQLException {

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
    public void dropDB(String databaseName) throws SQLException {

    }

    @Override
    public void dropTable(String tableName) throws SQLException {

    }

    @Override
    public Set<String> getDatabasesNames() throws SQLException {
        return null;
    }

    @Override
    public Set<String>/*String[]*/ getTableColumns(String tableName) {
//        return new String[] {"name", "password", "id"};
        return new LinkedHashSet<String>();
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
