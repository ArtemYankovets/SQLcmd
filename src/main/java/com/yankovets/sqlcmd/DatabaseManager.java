package com.yankovets.sqlcmd;

import java.sql.*;
import java.util.*;

public class DatabaseManager {

    private Connection connection;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String database = "sqlcmd";
        String user = "postgres";
        String password = "root";

        DatabaseManager manager = new DatabaseManager();
        manager.connect(database, user, password);

        Connection connection = manager.getConnection();

        // insert
        String sql = "INSERT INTO users (name, password)" +
            "VALUES ('Stiven', 'Pupkin')";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

        // select
        String[] tables = manager.getTableNames();
        System.out.println(Arrays.toString(tables));

        String tableName = "users";

        DataSet[] result = manager.getTableData(tableName);

        System.out.println(Arrays.toString(result));


        // delete
        sql = "DELETE FROM users " +
            "WHERE id > 10 AND id < 30";
        stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

        // update
        String sql1 = "UPDATE users SET password = ? WHERE id > 3";
        PreparedStatement ps = connection.prepareStatement(
            sql1);
        String pass = "password_" + new Random().nextInt();
        ps.setString(1, pass);
        ps.executeUpdate();
        ps.close();

        connection.close();
    }

    public DataSet[] getTableData(String tableName) throws SQLException {
        int size = getSize(tableName);

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        DataSet[] result = new DataSet[size];
        int index = 0;
        while (rs.next()) {
            DataSet dataSet = new DataSet();
            result[index++] = dataSet;
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
            }
        }
        rs.close();
        stmt.close();
        return result;
    }

    private int getSize(final String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT (*) FROM " + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    public String[] getTableNames() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables " +
                "WHERE table_schema='public' " +
                "AND table_type='BASE TABLE'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public void connect(final String database, final String user, final String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Please add jdbc jar to project.");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/" + database, user, password);
        } catch (SQLException e) {
            System.out.println(String.format("Cant get connection for database:%s, user:%s.", database, user));
            e.printStackTrace();
            connection = null;
        }
    }

    private Connection getConnection() {
        return connection;
    }
}