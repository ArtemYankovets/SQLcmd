package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DataSet;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Set;

public class GetTableData extends AbstractCommandImpl {

    public GetTableData(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "show|tableName";
    }

    @Override
    public String getCommandDescription() {
        return "for showing data from the table";
    }

    @Override
    public void process(String command) {
        validator.validate();
        String tableName = validator.getParametersOfCommandLine()[1];
        try {
            if (checkExistenceOfTableInDB(tableName)) {
                DataSet[] tableData = manager.getTableData(tableName);
                Set<String> tableColumns = manager.getTableColumns(tableName);
                printHeader(tableColumns);
                printTable(tableData);
            }
        } catch (SQLException e) {
            view.write(String.format("Error with showing data from table '%s' because: %s", tableName, e.getMessage()));
        }
    }

    private void printTable(DataSet[] tableData) {
        for (DataSet row : tableData) {
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

    private void printHeader(Set<String> tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("-------------------------");
        view.write(result);
        view.write("-------------------------");
    }

    private boolean checkExistenceOfTableInDB(String tableName) throws SQLException {
        Set<String> setOfTableNames = manager.getTablesNames();
        if (!setOfTableNames.contains(tableName)) {
            view.write(String.format("There is not the table with name '%s' in database.", tableName));
            return false;
        } else {
            return true;
        }
    }
}
