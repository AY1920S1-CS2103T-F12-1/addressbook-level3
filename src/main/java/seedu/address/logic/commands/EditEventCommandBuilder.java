package seedu.address.logic.commands;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.DateTimeArgumentBuilder;
import seedu.address.logic.commands.arguments.IndexVariableArguments;
import seedu.address.logic.commands.arguments.IndexVariableArgumentsBuilder;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringArgumentBuilder;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.StringVariableArgumentsBuilder;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.Model;
import seedu.address.model.events.DateTime;

/**
 * Represents a CommandBuilder responsible for creating {@link EditEventCommand}.
 */
class EditEventCommandBuilder extends CommandBuilder {

    public static final String OPTION_DESCRIPTION = "--description";
    public static final String OPTION_START_DATE_TIME = "--start";
    public static final String OPTION_END_DATE_TIME = "--end";
    public static final String OPTION_REMIND_DATE_TIME = "--remind";
    public static final String OPTION_TAGS = "--tag";

    private static final String ARGUMENT_INDEXES = "INDEXES";
    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_START_DATE_TIME = "START_DATE_TIME";
    private static final String ARGUMENT_END_DATE_TIME = "END_DATE_TIME";
    private static final String ARGUMENT_REMIND_DATE_TIME = "REMIND_DATE_TIME";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final Model model;

    private final IndexVariableArgumentsBuilder indexes;
    private final StringArgumentBuilder description;
    private final DateTimeArgumentBuilder start;
    private final DateTimeArgumentBuilder end;
    private final DateTimeArgumentBuilder remind;
    private final StringVariableArgumentsBuilder tags;

    EditEventCommandBuilder(Model model) {
        this.model = model;

        this.indexes = IndexVariableArguments.newBuilder(ARGUMENT_INDEXES);
        this.description = StringArgument.newBuilder(ARGUMENT_DESCRIPTION);
        this.start = DateTimeArgument.newBuilder(ARGUMENT_START_DATE_TIME);
        this.end = DateTimeArgument.newBuilder(ARGUMENT_END_DATE_TIME);
        this.remind = DateTimeArgument.newBuilder(ARGUMENT_REMIND_DATE_TIME);
        this.tags = StringVariableArguments.newBuilder(ARGUMENT_TAGS);
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder()
            .setVariableArguments(this.indexes);
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of(
            OPTION_DESCRIPTION, Option.newBuilder()
                .addArgument(this.description),
            OPTION_START_DATE_TIME, Option.newBuilder()
                .addArgument(this.start),
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

    List<Integer> getIndexes() {
        return this.indexes.getValues();
    }

    String getDescription() {
        return this.description.getValue();
    }

    DateTime getStart() {
        return this.start.getValue();
    }

    DateTime getEnd() {
        return this.end.getValue();
    }

    DateTime getRemind() {
        return this.remind.getValue();
    }

    List<String> getTags() {
        return this.tags.getValues();
    }

    @Override
    Command commandBuild() {
        return new EditEventCommand(this);
    }
}
