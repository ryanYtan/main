package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;

/**
 * Contains integration tests (interaction with the AddressModel) and unit tests for ListAddressCommand.
 */
public class ListAddressCommandTest {

    private AddressModel addressModel;
    private AddressModel expectedAddressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
    }

    /**
     * Verifies that checking ListAddressCommand for the availability of inverse execution returns false.
     */
    @Test
    public void test_hasInverseExecution() {
        ListAddressCommand listAddressCommand = new ListAddressCommand();
        assertFalse(listAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAddressCommand(), addressModel,
                ListAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListAddressCommand(), addressModel,
                ListAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    /**
     * Verifies that calling inverse execution of ListAddressCommand will always throw command exception with the
     * correct message.
     */
    @Test
    public void test_inverseExecute_exceptionThrown() {
        ListAddressCommand listAddressCommand = new ListAddressCommand();
        assertThrows(CommandException.class,
                ListAddressCommand.MESSAGE_NO_INVERSE, () -> listAddressCommand.executeInverse(addressModel));
    }
}
