package pl.wiktorszczeszek.core.domain.results;

import org.junit.jupiter.api.Test;
import pl.wiktorszczeszek.core.domain.PdfFile;
import pl.wiktorszczeszek.core.domain.SearchPhrase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextContentSearchTest {
    @Test
    void constructor_ShouldThrowException_WhenFileIsNull() {
        SearchPhrase phrase = new SearchPhrase("test");
        assertThrows(IllegalArgumentException.class, () -> new TextContentSearch(null, phrase));
    }

    @Test
    void constructor_ShouldThrowException_WhenPhraseIsNull() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        assertThrows(IllegalArgumentException.class, () -> new TextContentSearch(file, null));
    }

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        TextContentSearch result = new TextContentSearch(file, phrase);

        assertEquals(file, result.getFile());
        assertEquals(phrase, result.getPhrase());
        assertEquals(0, result.getOccurrenceCount());
        assertFalse(result.getIsSearched());
    }

    @Test
    void constructorWithOccurrenceCount_ShouldInitializeFieldsCorrectly() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        int occurrenceCount = 5;
        TextContentSearch result = new TextContentSearch(file, phrase, occurrenceCount);

        assertEquals(file, result.getFile());
        assertEquals(phrase, result.getPhrase());
        assertEquals(occurrenceCount, result.getOccurrenceCount());
        assertTrue(result.getIsSearched());
    }

    @Test
    void constructorWithOccurrenceCount_ShouldThrowException_WhenOccurrenceCountIsNegative() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        assertThrows(IllegalArgumentException.class, () -> new TextContentSearch(file, phrase, -1));
    }

    @Test
    void addOccurrence_ShouldUpdateCountBy1AndSetIsSearched() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        TextContentSearch result = new TextContentSearch(file, phrase);

        result.addOccurrence();
        assertEquals(1, result.getOccurrenceCount());
        assertTrue(result.getIsSearched());
    }

    @Test
    void addOccurrence_ShouldUpdateCountBy2AndSetIsSearched_WhenAddsBy2() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        TextContentSearch result = new TextContentSearch(file, phrase);

        result.addOccurrence(2);
        assertEquals(2, result.getOccurrenceCount());
        assertTrue(result.getIsSearched());
    }

    @Test
    void addOccurrence_ShouldThrowException_WhenAddsByNegative() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        TextContentSearch result = new TextContentSearch(file, phrase);

        assertThrows(IllegalArgumentException.class, () -> result.addOccurrence(-1));
    }

    @Test
    void equals_ShouldReturnTrue_ForSameFileValueAndPhraseRef() {
        PdfFile file1 = new PdfFile("/path/to/file.pdf");
        PdfFile file2 = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        TextContentSearch result1 = new TextContentSearch(file1, phrase);
        TextContentSearch result2 = new TextContentSearch(file2, phrase);

        assertEquals(result1, result2);
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentFilesValue() {
        PdfFile file1 = new PdfFile("/path/to/file1.pdf");
        PdfFile file2 = new PdfFile("/path/to/file2.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        TextContentSearch result1 = new TextContentSearch(file1, phrase);
        TextContentSearch result2 = new TextContentSearch(file2, phrase);

        assertNotEquals(result1, result2);
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentPhraseRef() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase1 = new SearchPhrase("test");
        SearchPhrase phrase2 = new SearchPhrase("test");

        TextContentSearch result1 = new TextContentSearch(file, phrase1);
        TextContentSearch result2 = new TextContentSearch(file, phrase2);

        assertNotEquals(result1, result2);
    }

    @Test
    void hashCode_ShouldReturnTrue_ForSameFileValueAndPhraseRef() {
        PdfFile file1 = new PdfFile("/path/to/file.pdf");
        PdfFile file2 = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        TextContentSearch result1 = new TextContentSearch(file1, phrase);
        TextContentSearch result2 = new TextContentSearch(file2, phrase);

        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void hashCode_ShouldReturnFalse_ForDifferentFilesValue() {
        PdfFile file1 = new PdfFile("/path/to/file1.pdf");
        PdfFile file2 = new PdfFile("/path/to/file2.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        TextContentSearch result1 = new TextContentSearch(file1, phrase);
        TextContentSearch result2 = new TextContentSearch(file2, phrase);

        assertNotEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void hashCode_ShouldReturnFalse_ForDifferentPhraseRef() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase1 = new SearchPhrase("test");
        SearchPhrase phrase2 = new SearchPhrase("test");

        TextContentSearch result1 = new TextContentSearch(file, phrase1);
        TextContentSearch result2 = new TextContentSearch(file, phrase2);

        assertNotEquals(result1.hashCode(), result2.hashCode());
    }
}