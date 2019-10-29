package seedu.address.logic.commands;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link AddTaskCommand}.
 */
class AddTaskCommandBuilder extends CommandBuilder {

    public static final String OPTION_TAGS = "--tag";
    public static final String OPTION_DUE_DATE_TIME = "--due";

    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_DUE_DATE_TIME = "DUE_DATE_TIME";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final ModelManager model;

    private String description;
    private DateTime due;
    private List<String> tags;

    AddTaskCommandBuilder(ModelManager model) {
        this.model = model;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
            .addArgument(StringArgument.newBuilder(ARGUMENT_DESCRIPTION, v -> this.description = v));
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return Map.of(
            OPTION_TAGS, ArgumentList.optional()
                .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, v -> this.tags = v)),
            OPTION_DUE_DATE_TIME, ArgumentList.optional()
                .addArgument(DateTimeArgument.newBuilder(ARGUMENT_DUE_DATE_TIME, v -> this.due = v))
        );
    }

    ModelManager getModel() {
        return this.model;
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
        return new AddTaskCommand(this);
    }
}
