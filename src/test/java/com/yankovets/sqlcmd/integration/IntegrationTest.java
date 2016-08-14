package com.yankovets.sqlcmd.integration;

import com.yankovets.sqlcmd.controller.Main;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup(){
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
        assertEquals("Hello, user!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                "Exist commands:\r\n" +
                // help
                "\tconnect|databaseName|username|password\r\n" +
                "\t\tfor connection to database, which we planning to work with\r\n" +
                "\tlist\r\n" +
                "\t\tfor getting all tables from database, witch you got connection\r\n" +
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
        assertEquals("Hello, user!\r\n" +
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
        assertEquals("Hello, user!\r\n" +
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
        assertEquals("Hello, user!\r\n" +
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
        assertEquals("Hello, user!\r\n" +
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
        assertEquals("Hello, user!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success!\r\n" +
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
        assertEquals("Hello, user!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // list
                "[users, test]\r\n" +
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
        assertEquals("Hello, user!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // find|users
                "-------------------------\r\n" +
                "|name|password|id|\r\n" +
                "-------------------------\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }

    @Ignore
    @Test
    public void testFindWithErrorAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|root");
        in.add("find|nonexistent");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello, user!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connet|
                "Success!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // find|nonexistent
                "\r\n" +
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
        assertEquals("Hello, user!\r\n" +
                "Please input database name, user name and password in format: connect|database|userName|password\r\n" +
                // connect|sqlcmd|postgres|root
                "Success!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // list
                "[users, test]\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // connect|test|postgres|root
                "Success!\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // list
                "[qwe]\r\n" +
                "Input command (or 'help' for help):\r\n" +
                // exit
                "See you next time!\r\n", getData());
    }
}
