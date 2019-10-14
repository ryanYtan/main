package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.exceptions.InvalidFrequencyException;
import seedu.jarvis.model.planner.exceptions.InvalidPriorityException;

class TodoTest {

    @Test
    void addPriority() {
        Todo t = new Todo("homework");
        t.addPriority("high");
        assertNotNull(t.priority);
    }

    @Test
    void addPriority_invalidInput_exceptionThrown() {
        Todo t = new Todo("homework");
        assertThrows(InvalidPriorityException.class, () -> t.addPriority("highest"));
    }

    @Test
    void addFrequency() {
        Todo t = new Todo("homework");
        t.addFrequency("weekly");
        assertNotNull(t.frequency);
    }

    @Test
    void addFrequency_invalidInput_exceptionThrown() {
        Todo t = new Todo("homework");
        assertThrows(InvalidFrequencyException.class, () -> t.addFrequency("every week"));
    }

    @Test
    void addTag() {
        Todo t = new Todo("homework");
        Tag tag = new Tag("school");
        t.addTag(tag);
        assertNotNull(t.tags);
    }

    @Test
    void getTags() {
        Todo t = new Todo("homework");
        Tag tag = new Tag("school");
        t.addTag(tag);
        assertTrue(t.getTags().contains(tag));
    }

    @Test
    void isEqual_validInput_true() {
        Todo tOne = new Todo("borrow book");
        Todo tTwo = new Todo("borrow book");
        assertTrue(tOne.isEqual(tTwo));
    }

    @Test
    void isEqual_validInput_false() {
        Todo tOne = new Todo("hello");
        Todo tTwo = new Todo("hi there");
        assertFalse(tOne.isEqual(tTwo));

    }
}