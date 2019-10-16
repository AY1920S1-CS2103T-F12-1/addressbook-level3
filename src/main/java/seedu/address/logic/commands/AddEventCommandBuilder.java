package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.DateTimeArgumentBuilder;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringArgumentBuilder;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.StringVariableArgumentsBuilder;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.Model;
import seedu.address.model.events.DateTime;

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

    private final Model model;

    private final StringArgumentBuilder description;
    private final DateTimeArgumentBuilder start;
    private final DateTimeArgumentBuilder end;
    private final DateTimeArgumentBuilder remind;
    private final StringVariableArgumentsBuilder tags;

    AddEventCommandBuilder(Model model) {
        this.model = model;

        this.description = StringArgument.newBuilder(ARGUMENT_DESCRIPTION);
        this.start = DateTimeArgument.newBuilder(ARGUMENT_START_DATE_TIME);
        this.end = DateTimeArgument.newBuilder(ARGUMENT_END_DATE_TIME);
        this.remind = DateTimeArgument.newBuilder(ARGUMENT_REMIND_DATE_TIME);
        this.tags = StringVariableArguments.newBuilder(ARGUMENT_TAGS);
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder()
            .addArgument(this.description)
            .addArgument(this.start);
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of(
            OPTION_END_DATE_TIME, Option.newBuilder()
                .addArgument(this.end),
            OPTION_REMIND_DATE_TIME, Option.newBuilder()
                .addArgument(this.remind),
            OPTION_TAGS, Option.newBuilder()
                .setVariableArguments(this.tags)
        );
    }

    Model getModel() {
        return model;
    }

    String getDescription() {
        return this.description.getValue();
    }

    DateTime getStart() {
        return this.start.getValue();
    }

    @Override
    Command commandBuild() {
        return new AddEventCommand(this);
    }
}
