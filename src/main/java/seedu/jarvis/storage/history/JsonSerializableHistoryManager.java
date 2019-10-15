package seedu.jarvis.storage.history;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedAddAddressCommand;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedClearAddressCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * A {@code HistoryManager} that is serializable to JSON format.
 */
@JsonRootName(value = "historymanager")
public class JsonSerializableHistoryManager {

    public static final String MESSAGE_INVALID_COMMAND = "Invalid Command";

    private final List<JsonAdaptedCommand> executedCommands = new ArrayList<>();
    private final List<JsonAdaptedCommand> inverselyExecutedCommands = new ArrayList<>();

    @JsonCreator
    public JsonSerializableHistoryManager(@JsonProperty("executedCommands") List<JsonAdaptedCommand> executedCommands,
                                          @JsonProperty("inverselyExecutedCommands")
                                                  List<JsonAdaptedCommand> inverselyExecutedCommands) {
        this.executedCommands.addAll(executedCommands);
        this.inverselyExecutedCommands.addAll(inverselyExecutedCommands);
    }

    public JsonSerializableHistoryManager(HistoryManager historyManager) throws InvalidCommandToJsonException {
        List<Command> executedCommands = historyManager.getExecutedCommands().getCommands();
        List<Command> inverselyExecutedCommands = historyManager.getInverselyExecutedCommands().getCommands();
        for (Command command : executedCommands) {
            this.executedCommands.add(convertToJsonAdaptedCommand(command));
        }
        for (Command command : inverselyExecutedCommands) {
            this.inverselyExecutedCommands.add(convertToJsonAdaptedCommand(command));
        }
    }

    private JsonAdaptedCommand convertToJsonAdaptedCommand(Command command) throws InvalidCommandToJsonException {
        switch (command.getCommandWord()) {
        case AddAddressCommand.COMMAND_WORD:
            return new JsonAdaptedAddAddressCommand(command);
        case ClearAddressCommand.COMMAND_WORD:
            return new JsonAdaptedClearAddressCommand(command);
        default:
            throw new InvalidCommandToJsonException(MESSAGE_INVALID_COMMAND);
        }
    }

    /**
     * Converts this Jackson-friendly adapted {@code HistoryManager} object.
     *
     * @return {@code HistoryManager} of the Jackson-friendly adapted {@code JsonSerializableHistoryManager}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted {@code HistoryManager}.
     */
    public HistoryManager toModelType() throws IllegalValueException {
        HistoryManager historyManager = new HistoryManager();
        for (JsonAdaptedCommand jsonAdaptedExecutedCommand : executedCommands) {
            historyManager.rememberExecutedCommand(jsonAdaptedExecutedCommand.toModelType());
        }
        for (JsonAdaptedCommand jsonAdaptedInverselyExecutedCommand : inverselyExecutedCommands) {
            historyManager.rememberInverselyExecutedCommand(jsonAdaptedInverselyExecutedCommand.toModelType());
        }
        return historyManager;
    }
}
