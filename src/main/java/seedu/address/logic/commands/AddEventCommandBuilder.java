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
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class AddEventCommandBuilder extends CommandBuilder {

    public static final String OPTION_END_DATE_TIME = "--end";
    public static final String OPTION_REMIND_DATE_TIME = "--remind";
    public static final String OPTION_TAGS = "--tag";

    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_START_DATE_TIME = "START_DATE_TIME";
    private static final String ARGUMENT_END_DATE_TIME = "END_DATE_TIME";
    private static final String ARGUMENT_REMIND_DATE_TIME = "REMIND_DATE_TIME";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final ModelManager model;

    private String description;
    private DateTime start;
    private DateTime end;
    private DateTime remind;
    private List<String> tags;

    AddEventCommandBuilder(ModelManager model) {
        this.model = model;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
            .addArgument(StringArgument.newBuilder(ARGUMENT_DESCRIPTION, v -> this.description = v))
            .addArgument(DateTimeArgument.newBuilder(ARGUMENT_START_DATE_TIME, v -> this.start = v));
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return Map.of(
            OPTION_END_DATE_TIME, ArgumentList.optional()
                .addArgument(DateTimeArgument.newBuilder(ARGUMENT_END_DATE_TIME, v -> this.end = v)),
            OPTION_REMIND_DATE_TIME, ArgumentList.optional()
                .addArgument(DateTimeArgument.newBuilder(ARGUMENT_REMIND_DATE_TIME, v -> this.remind = v)),
            OPTION_TAGS, ArgumentList.optional()
                .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, v -> this.tags = v))
        );
    }

    ModelManager getModel() {
        return model;
    }

    String getDescription() {
        return this.description;
    }

    DateTime getStart() {
        return this.start;
    }

    DateTime getEnd() {
        return end;
    }

    DateTime getRemind() {
        return this.remind;
    }

    List<String> getTags() {
        return tags;
    }

    @Override
    Command commandBuild() {
        return new AddEventCommand(this);
    }
}
