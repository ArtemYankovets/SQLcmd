package com.yankovets.sqlcmd.controller.command.utility;

public class CommandValidation {

    private String commandTemplate;
    private String commandLine;
    private final String[] PARAMETERS_OF_COMMAND_LINE;

    public CommandValidation(String commandLine, String commandTemplate) {
        this.commandLine = commandLine;
        this.commandTemplate = commandTemplate;
        PARAMETERS_OF_COMMAND_LINE = splitCommandUpOnParameters(commandLine);
    }

    public void validate() {
        commandFormatValidation();
    }

    private void commandFormatValidation() {
        String[] paramOfCommandTemplate = splitCommandUpOnParameters(commandTemplate);

        if (paramOfCommandTemplate.length != PARAMETERS_OF_COMMAND_LINE.length) {
            throw new IllegalArgumentException(String.format("The amount of parameters for this command," +
                            " which split by '|' are %s, but expected %s",
                    paramOfCommandTemplate.length, PARAMETERS_OF_COMMAND_LINE.length));
            // A number of parameters is ...
        }
    }

    public String[] splitCommandUpOnParameters(String string){
        return string.split("[|]");
    }

    public void checkNumberOfParameters() {
        if (getParametersOfCommandLine().length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Must be even number of parameters " +
                    "in format 'createEntry|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "you typed: '%s'", commandLine));
        }
    }

   /* private boolean checkExistenceOfElementInDB (String string, DatabaseManager manager) throws SQLException {
        Set<String> setOfTableNames = manager.getTablesNames();
        if (!setOfTableNames.contains(string)) {
            view.write(String.format("There is not the table with name '%s' in database.", tableName));
            return false;
        } else {
            return true;
        }
    }*/

    public String[] getParametersOfCommandLine() {
        return PARAMETERS_OF_COMMAND_LINE;
    }
}
