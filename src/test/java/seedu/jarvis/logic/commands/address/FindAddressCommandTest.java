package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalPersons.CARL;
import static seedu.jarvis.testutil.TypicalPersons.ELLE;
import static seedu.jarvis.testutil.TypicalPersons.FIONA;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;
import seedu.jarvis.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the AddressModel) for {@code FindAddressCommand}.
 */
public class FindAddressCommandTest {
    private AddressModel addressModel;
    private AddressModel expectedAddressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /**
     * Verifies that checking FindAddressCommand for the availability of inverse execution returns false.
     */
    @Test
    public void test_hasInverseExecution() {
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAddressCommand findAddressCommand = new FindAddressCommand(predicate);
        assertFalse(findAddressCommand.hasInverseExecution());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindAddressCommand findFirstCommand = new FindAddressCommand(firstPredicate);
        FindAddressCommand findSecondCommand = new FindAddressCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAddressCommand findFirstCommandCopy = new FindAddressCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedAddressModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, addressModel, expectedMessage, expectedAddressModel);
        assertEquals(Collections.emptyList(), addressModel.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedAddressModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, addressModel, expectedMessage, expectedAddressModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), addressModel.getFilteredPersonList());
    }

    /**
     * Verifies that calling inverse execution of FindAddressCommand will always throw command exception with the
     * correct message.
     */
    @Test
    public void test_executeInverse_exceptionThrown() {
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAddressCommand findAddressCommand = new FindAddressCommand(predicate);
        assertThrows(CommandException.class,
                FindAddressCommand.MESSAGE_NO_INVERSE, () -> findAddressCommand.executeInverse(addressModel));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
