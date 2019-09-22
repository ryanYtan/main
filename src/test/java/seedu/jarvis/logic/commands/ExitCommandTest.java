package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;

public class ExitCommandTest {
    private AddressModel addressModel;
    private AddressModel expectedAddressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager();
        expectedAddressModel = new AddressModelManager();
    }

    /**
     * Verifies that checking ExitCommand for the availability of inverse execution returns false.
     */
    @Test
    public void test_hasInverseExecution() {
        ExitCommand exitCommand = new ExitCommand();
        assertFalse(exitCommand.hasInverseExecution());
    }

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), addressModel, expectedCommandResult, expectedAddressModel);
    }

    /**
     * Verifies that calling inverse execution of ExitCommand will always throw command exception with the correct
     * message.
     */
    @Test
    public void test_executeInverse_exceptionThrown() {
        ExitCommand exitCommand = new ExitCommand();
        assertThrows(CommandException.class,
                ExitCommand.MESSAGE_NO_INVERSE, () -> exitCommand.executeInverse(addressModel));
    }
}
