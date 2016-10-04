package com.yankovets.sqlcmd.model;

import java.sql.SQLException;
import java.util.Set;

public interface DatabaseManager {
    DataSet[] getTableData(String tableName);

    Set<String> getTableNames();

    void connect(String database, String userName, String password) throws SQLException;

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);

    boolean isConnected();
}
