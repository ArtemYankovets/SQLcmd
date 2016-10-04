package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DataSet;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.util.Set;

public class Find implements Command{

    private static String COMMAND_FIND_SAMPLE = "find|tableName";

    private DatabaseManager manager;
    private View view;


    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("[|]");

        if (data.length != count()){
            throw new IllegalArgumentException("Command format 'find|tableName', but you taped: " + command);
        }

        String tableName = data[1];

        if (commandValidation(tableName)) {
            DataSet[] tableData = manager.getTableData(tableName);
            String[] tableColumns = manager.getTableColumns(tableName);

            printHeader(tableColumns);
            printTable(tableData);
        }
    }

    private int count() {
        return COMMAND_FIND_SAMPLE.split("[|]").length;
    }

    private void printTable(DataSet[] tableData) {
        for (DataSet row: tableData){
            printRow(row);
        }
        view.write("-------------------------");
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("-------------------------");
        view.write(result);
        view.write("-------------------------");
    }

    private boolean commandValidation (String tableName) {
        Set<String> setOfTableNames = manager.getTableNames();
        if (!setOfTableNames.contains(tableName)) {
            view.write(String.format("There is not the table with name '%s' in database.", tableName));
            return false;
        } else {
            return true;
        }
    }
}
