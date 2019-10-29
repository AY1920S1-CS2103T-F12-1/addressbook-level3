package seedu.address.logic.commands;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.IndexVariableArguments;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link EditTaskCommand}.
 */
class EditTaskCommandBuilder extends CommandBuilder {

    public static final String OPTION_DESCRIPTION = "--description";
    public static final String OPTION_DUE_DATE_DATE_TIME = "--duedate";
    public static final String OPTION_TAGS = "--tag";

    private static final String ARGUMENT_INDEXES = "INDEXES";
    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_DUE_DATE_DATE_TIME = "DUE_DATE_DATE_TIME";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final ModelManager model;

    private List<Integer> indexes;
    private String description;
    private DateTime due;
    private List<String> tags;

    EditTaskCommandBuilder(ModelManager model) {
        this.model = model;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
                .setVariableArguments(IndexVariableArguments.newBuilder(ARGUMENT_INDEXES, o -> this.indexes = o));
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return Map.of(
                OPTION_DESCRIPTION, ArgumentList.optional()
                        .addArgument(StringArgument.newBuilder(ARGUMENT_DESCRIPTION, o -> this.description = o)),
                OPTION_DUE_DATE_DATE_TIME, ArgumentList.optional()
                        .addArgument(DateTimeArgument.newBuilder(ARGUMENT_DUE_DATE_DATE_TIME, o -> this.due = o)),
                OPTION_TAGS, ArgumentList.optional()
                        .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, o -> this.tags = o))
        );
    }

    ModelManager getModel() {
        return model;
    }

    List<Integer> getIndexes() {
        return this.indexes;
    }

    String getDescription() {
        return this.description;
    }

    DateTime getDueDate() {
        return this.due;
    }

    List<String> getTags() {
        return this.tags;
    }

    @Override
    Command commandBuild() {
        return new EditTaskCommand(this);
    }
}
