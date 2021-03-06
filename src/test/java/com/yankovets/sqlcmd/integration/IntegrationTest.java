package com.yankovets.sqlcmd.integration;

import com.yankovets.sqlcmd.Main;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databasemanager;

    @Before
    public void setup(){
        databasemanager = new JDBCDatabaseManager();

        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }


    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testHelp(){
        // given
        in.add("help");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                "Exist commands:\r\n" +
                // help
                "\tconnect|databaseName|username|password\r\n" +
                "\t\tfor connection to database, which we planning to work with\r\n" +
                "\tlist\r\n" +
                "\t\tfor getting all tables from database, witch you got connection\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tfor all table clearing\r\n" +
                "\tcreateEntry|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\tfor creating notes in database\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tfor getting the data from table 'tableName'\r\n" +
                "\thelp\r\n" +
                "\t\tfor outputing this list of commend to screen\r\n" +
                "\texit\r\n" +
                "\t\tfor exit from application\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testExit() {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testListWithoutConnect() {
        // given
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // list
                "You can't use the command 'list' until you connect to database with command 'connect|databaseName|username|password'\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testFindWithoutConnect() {
        // given
        in.add("find|users");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // find|
                "You can't use the command 'find|users' until you connect to database with command 'connect|databaseName|username|password'\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testUnsupported() {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // unsupported
                "You can't use the command 'unsupported' until you connect to database with command 'connect|databaseName|username|password'\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|root");
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // unsupported
                "Non exist command: unsupported\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testListAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|root");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // list
                "[test, users]\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|root");
        in.add("find|users");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // find|users
                "-------------------------\r\n" +
                "|name|password|id|\r\n" +
                "-------------------------\r\n" +
                "-------------------------\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testFindWithError() {
        // given
        in.add("connect|sqlcmd|postgres|root");
        in.add("find|qwe|qwqw");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // find|nonexistent
                "Fail! The cause of: Command format 'find|tableName', but you taped: find|qwe|qwqw\r\n" +
                "Try again!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testConnectAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|root");
        in.add("list");
        in.add("connect|test|postgres|root");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connect|sqlcmd|postgres|root
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // list
                "[test, users]\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // connect|test|postgres|root
                "Success! Got connection for database: test, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // list
                "[qwe]\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testConnectWithError() {
        // given
        in.add("connect|sqlcmd");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connect|sqlcmd
                "Fail! The cause of: The amount of arguments for this command, which split by '|' are 2, but expected 4\r\n" +
                "Try again!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect_WithData() {
        // given
//        databasemanager.connect("sqlcmd", "postgres", "root");
//
//        databasemanager.clear("users");
//
//        DataSet user1 = new DataSet();
//        user1.put("id", "13");
//        user1.put("name", "Stiven");
//        user1.put("password", "*****");
//        databasemanager.createEntry("users", user1);
//
//        DataSet user2 = new DataSet();
//        user2.put("id", "14");
//        user2.put("name", "Eva");
//        user2.put("password", "+++++");
//        databasemanager.createEntry("users", user2);

        in.add("connect|sqlcmd|postgres|root");
        in.add("clear|users");
        in.add("Y");
        in.add("createEntry|users|id|13|name|Stiven|password|*****");
        in.add("createEntry|users|id|14|name|Eva|password|+++++");
        in.add("find|users");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                //clear|users
                "Attention! You are going to delete all data from the table 'users'. Are you sure? [ Y / N ]\r\n" +
                //Y
                "Table users was successfully cleared\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // createEntry|users|id|13|name|Stiven|password|*****
                "Note {names:[id, name, password], values:[13, Stiven, *****]} was successfully created in table 'users'\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // createEntry|users|id|14|name|Eva|password|+++++
                "Note {names:[id, name, password], values:[14, Eva, +++++]} was successfully created in table 'users'\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // find|users
                "-------------------------\r\n" +
                "|name|password|id|\r\n" +
                "-------------------------\r\n" +
                "|Stiven|*****|13|\r\n" +
                "|Eva|+++++|14|\r\n" +
                "-------------------------\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testClearWithError() {
        // given
        in.add("connect|sqlcmd|postgres|root");
        in.add("clear|qwe|qwqw");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // clear|qwe|qwqw
                "Fail! The cause of: Command format 'clear|tableName', but you taped: clear|qwe|qwqw\r\n" +
                "Try again!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Test
    public void testCreateWithError() {
        // given

        in.add("connect|sqlcmd|postgres|root");
        in.add("createEntry|users|error");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success! Got connection for database: sqlcmd, user: postgres.\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // createEntry|users|error
                "Fail! The cause of: Must be even amount of parameters in format 'createEntry|tableName|column1|value1|column2|value2|...|columnN|valueN', you typed: 'createEntry|users|error'\r\n" +
                "Try again!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }
}
