package com.yankovets.sqlcmd.model;

import java.sql.SQLException;
import java.util.Set;

public interface DatabaseManager {
    void connect(String database, String userName, String password) throws SQLException;

    void create(String tableName, DataSet input) throws SQLException;

    void createDB(String databaseName) throws SQLException;

    void createTable(String tableName) throws SQLException;

    void update(String tableName, int id, DataSet newValue) throws SQLException;

    void dropDB(String databaseName) throws SQLException;

    void dropTable(String tableName) throws SQLException;

    Set<String> getDatabasesNames() throws SQLException;

    Set<String> getTablesNames() throws SQLException;

    DataSet[] getTableData(String tableName) throws SQLException;

    Set<String> getTableColumns(String tableName) throws SQLException;

    void clear(String tableName) throws SQLException;

    boolean isConnected();
}
