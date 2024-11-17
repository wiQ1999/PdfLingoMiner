package pl.wiktorszczeszek.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchPhraseTest {
    @Test
    void constructor_ShouldThrowException_WhenPhraseIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SearchPhrase(null);
        });
    }

    @Test
    void getPhrase_ShouldReturnTrimmedPhrase() {
        SearchPhrase searchPhrase = new SearchPhrase("  test phrase  ");
        assertEquals("test phrase", searchPhrase.getPhrase());
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPhraseHas1Character() {
        String phrase = "a".repeat(1);
        SearchPhrase searchPhrase = new SearchPhrase(phrase);
        boolean isValid = searchPhrase.isValid();
        assertFalse(isValid);
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPhraseHas101Character() {
        String phrase = "a".repeat(101);
        SearchPhrase searchPhrase = new SearchPhrase(phrase);
        boolean isValid = searchPhrase.isValid();
        assertFalse(isValid);
    }

    @Test
    void isValid_ShouldReturnTrue_WhenPhraseHas2Character() {
        String phrase = "a".repeat(2);
        SearchPhrase searchPhrase = new SearchPhrase(phrase);
        boolean isValid = searchPhrase.isValid();
        assertTrue(isValid);
    }

    @Test
    void isValid_ShouldReturnTrue_WhenPhraseHas100Character() {
        String phrase = "a".repeat(100);
        SearchPhrase searchPhrase = new SearchPhrase(phrase);
        boolean isValid = searchPhrase.isValid();
        assertTrue(isValid);
    }
}
