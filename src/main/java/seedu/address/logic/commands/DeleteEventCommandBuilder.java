package seedu.address.logic.commands;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.arguments.IndexVariableArguments;
import seedu.address.logic.commands.arguments.IndexVariableArgumentsBuilder;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.StringVariableArgumentsBuilder;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.Model;

/**
 * Represents a CommandBuilder responsible for creating {@link DeleteEventCommand}.
 */
class DeleteEventCommandBuilder extends CommandBuilder {

    public static final String OPTION_TAGS = "--tag";

    private static final String ARGUMENT_INDEXES = "INDEXES";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final Model model;
    private final IndexVariableArgumentsBuilder indexes;
    private final StringVariableArgumentsBuilder tags;

    DeleteEventCommandBuilder(Model model) {
        this.model = model;
        this.indexes = IndexVariableArguments.newBuilder(ARGUMENT_INDEXES);
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

    List<String> getTags() {
        return this.tags.getValues();
    }

    @Override
    Command commandBuild() {
        return new DeleteEventCommand(this);
    }
}
