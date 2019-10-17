package seedu.jarvis.model.planner.tasks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;

class TodoTest {

    @Test
    void addPriority() {
        Todo t = new Todo("homework");
        t.addPriority(Priority.HIGH);
        assertNotNull(t.priority);
    }

    @Test
    void addFrequency() {
        Todo t = new Todo("homework");
        t.addFrequency(Frequency.DAILY);
        assertNotNull(t.frequency);
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
    void equals_validInput_true() {
        Todo tOne = new Todo("borrow book");
        Todo tTwo = new Todo("borrow book");
        assertTrue(tOne.equals(tTwo));
    }

    @Test
    void equals_validInput_false() {
        Todo tOne = new Todo("hello");
        Todo tTwo = new Todo("hi there");
        assertFalse(tOne.equals(tTwo));

    }
}