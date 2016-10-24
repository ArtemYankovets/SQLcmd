package com.yankovets.sqlcmd.controller.command;

import com.yankovets.sqlcmd.model.DataSet;
import com.yankovets.sqlcmd.model.DatabaseManager;
import com.yankovets.sqlcmd.view.View;

import java.sql.SQLException;

public class CreateEntry extends AbstractCommandImpl {

    public CreateEntry(DatabaseManager databaseManager, View view) {
        super(databaseManager, view);
    }

    @Override
    public String getCommandTemplate() {
        return "createEntry|tableName|column1|value1|column2|value2|...|columnN|valueN";
    }

    @Override
    public String getCommandDescription() {
        return "for creating entry in database";
    }

    @Override
    public void process(String command) {
        validator.checkNumberOfParameters();
        String[] paramOfCommandLine = validator.getParametersOfCommandLine();
        String tableName = paramOfCommandLine[1];

        DataSet dataSet = makeDataSet(paramOfCommandLine);
        try {
            manager.createEntry(tableName, dataSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        view.write(String.format("Entry %s was successfully created in table '%s'", dataSet, tableName));
    }

    private DataSet makeDataSet(String[] data){
        DataSet result = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            result.put(columnName, value);
        }
        return result;
    }
}
