package com.yankovets.sqlcmd.model;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class JDBCDatabaseManager implements DatabaseManager {

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5433/";

    private Connection connection;

    @Override
    public void clear(String tableName) throws SQLException {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + tableName);
        }
    }

    @Override
    public void connect(String databaseName, String host, String port, String userName, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }

        if (connection != null) {
            connection.close();
        }

        connection = getConnection(databaseName, host, port, userName, password);

    }

    private Connection getConnection (String databaseName, String host, String port, String userName, String password) throws SQLException {
        String url = compileString("jdbc:postgresql://", host, ":", port, "/", databaseName);
        return DriverManager.getConnection(url, userName, password);
    }

    private String compileString(String ... args) {
        StringBuilder result = new StringBuilder();
        for (String str: args) {
            result.append(str);
        }
        return result.toString();
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    private void checkConnection() throws SQLException{
        if (connection == null){
            throw new SQLException("You do not connect to database.");
        }
    }

    @Override
    public void createDB(String databaseName) throws SQLException {
        checkConnection();
        String sql = "CREATE DATABASE " + databaseName;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    @Override
    public void createTable(String tableName) throws SQLException {
        checkConnection();
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    @Override
    public void createEntry(String tableName, DataSet input) throws SQLException {
        checkConnection();
        String tableNames = getNamesFormated(input, "%s,");
        String values = getValuesFormated(input, "'%s',");
        String sql = "INSERT INTO " + tableName + " (" + tableNames + ")" + "VALUES (" + values + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) throws SQLException {
        checkConnection();
        String tableNames = getNamesFormated(newValue, "%s = ?,");
        String sql = "UPDATE " + tableName + " SET " + tableNames + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index++, value);
            }
            ps.setInt(index, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void dropDB(String databaseName) throws SQLException {
        checkConnection();
        String sql = "DROP DATABASE IF EXISTS " + databaseName;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    @Override
    public void dropTable(String tableName) throws SQLException {
        checkConnection();
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    @Override
    public Set<String> getDatabasesNames() throws SQLException{
        checkConnection();
        String sql =
                "SELECT datname " +
                "FROM pg_database " +
                "WHERE datistemplate = false;";
        try (Statement ps = connection.createStatement();
             ResultSet rs = ps.executeQuery(sql)) {
            Set<String> result = new LinkedHashSet<>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        }
    }

    @Override
    public Set<String> getTablesNames() throws SQLException {
        checkConnection();
        Set<String> result = new TreeSet<>();
        String sql =
                "SELECT table_name " +
                "FROM information_schema.tables " +
                "WHERE table_schema='public' " +
                "AND table_type='BASE TABLE'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(rs.getString("table_name"));
            }
            return result;
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) throws SQLException {
        checkConnection();
        Set<String> result = new TreeSet<>();
        String sql =
                "SELECT * " +
                "FROM information_schema.columns " +
                "WHERE table_schema='public' " +
                "AND table_name='" + tableName + "'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int index = 0;
            while (rs.next()) {
                result.add(rs.getString("column_name"));
            }
            return result;
        }
    }

    @Override
    public DataSet[] getTableData(String tableName) throws SQLException {
        checkConnection();
        String sql = "SELECT * FROM " + tableName;
        DataSet[] result = new DataSet[getSize(tableName)];
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData rsmd = rs.getMetaData();

            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            return result;
        }
    }

    private int getSize(String tableName) throws SQLException {
        checkConnection();
        String sql = "SELECT COUNT (*) FROM " + tableName;
        int size = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet rsCount = stmt.executeQuery(sql)) {
            rsCount.next();
            size = rsCount.getInt(1);
            return size;
        }
    }


    private String getNamesFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }
}