package com.yankovets.sqlcmd;

import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/sqlcmd", "postgres",
            "root");

        // insert
        String sql = "INSERT INTO users (name, password)" +
            "VALUES ('Stiven', 'Pupkin')";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

        // select
        sql = "SELECT * FROM users WHERE id > 5";
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println("id:" + rs.getString("id"));
            System.out.println("name:" + rs.getString("name"));
            System.out.println("password:" + rs.getString("password"));
            System.out.println("-----");
        }
        rs.close();
        stmt.close();

        // table names
        stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables" +
            " WHERE table_schema = 'public' AND table_type = 'BASE TABLE'");
        String[] tables = new String[100];
        int intdex = 0;
        while (rs.next()) {
            tables[intdex++] = rs.getString("table_name");
        }
        tables = Arrays.copyOf(tables, intdex, String[].class);
        System.out.println(Arrays.toString(tables));
        rs.close();
        stmt.close();

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
}
