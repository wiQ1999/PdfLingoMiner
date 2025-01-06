package pl.wiktorszczeszek.core.domain.results;

import org.junit.jupiter.api.Test;
import pl.wiktorszczeszek.core.domain.PdfFile;
import pl.wiktorszczeszek.core.domain.SearchPhrase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileNameSearchTest {
    @Test
    void constructor_ShouldThrowException_WhenFileIsNull() {
        SearchPhrase phrase = new SearchPhrase("test");
        assertThrows(IllegalArgumentException.class, () -> new FileNameSearch(null, phrase));
    }

    @Test
    void constructor_ShouldThrowException_WhenPhraseIsNull() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        assertThrows(IllegalArgumentException.class, () -> new FileNameSearch(file, null));
    }

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        FileNameSearch result = new FileNameSearch(file, phrase);

        assertEquals(file, result.getFile());
        assertEquals(phrase, result.getPhrase());
        assertFalse(result.getIsOccurrence());
        assertFalse(result.getIsSearched());
    }

    @Test
    void constructorWithOccurrence_ShouldInitializeFieldsCorrectly() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        boolean isOccurrence = true;
        FileNameSearch result = new FileNameSearch(file, phrase, isOccurrence);

        assertEquals(file, result.getFile());
        assertEquals(phrase, result.getPhrase());
        assertEquals(isOccurrence, result.getIsOccurrence());
        assertTrue(result.getIsSearched());
    }

    @Test
    void setIsOccurrence_ShouldSetIsOccurrenceAndSetIsSearched() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        FileNameSearch result = new FileNameSearch(file, phrase);

        result.setIsOccurrence();
        assertTrue(result.getIsOccurrence());
        assertTrue(result.getIsSearched());
    }

    @Test
    void setIsOccurrence_ShouldSetIsOccurrenceAndSetIsSearched_WhenSetFalse() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");
        FileNameSearch result = new FileNameSearch(file, phrase);

        result.setIsOccurrence(false);
        assertFalse(result.getIsOccurrence());
        assertTrue(result.getIsSearched());
    }

    @Test
    void equals_ShouldReturnTrue_ForSameFileValueAndPhraseRef() {
        PdfFile file1 = new PdfFile("/path/to/file.pdf");
        PdfFile file2 = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        FileNameSearch result1 = new FileNameSearch(file1, phrase);
        FileNameSearch result2 = new FileNameSearch(file2, phrase);

        assertEquals(result1, result2);
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentFilesValue() {
        PdfFile file1 = new PdfFile("/path/to/file1.pdf");
        PdfFile file2 = new PdfFile("/path/to/file2.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        FileNameSearch result1 = new FileNameSearch(file1, phrase);
        FileNameSearch result2 = new FileNameSearch(file2, phrase);

        assertNotEquals(result1, result2);
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentPhraseRef() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase1 = new SearchPhrase("test");
        SearchPhrase phrase2 = new SearchPhrase("test");

        FileNameSearch result1 = new FileNameSearch(file, phrase1);
        FileNameSearch result2 = new FileNameSearch(file, phrase2);

        assertNotEquals(result1, result2);
    }

    @Test
    void hashCode_ShouldReturnTrue_ForSameFileValueAndPhraseRef() {
        PdfFile file1 = new PdfFile("/path/to/file.pdf");
        PdfFile file2 = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        FileNameSearch result1 = new FileNameSearch(file1, phrase);
        FileNameSearch result2 = new FileNameSearch(file2, phrase);

        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void hashCode_ShouldReturnFalse_ForDifferentFilesValue() {
        PdfFile file1 = new PdfFile("/path/to/file1.pdf");
        PdfFile file2 = new PdfFile("/path/to/file2.pdf");
        SearchPhrase phrase = new SearchPhrase("test");

        FileNameSearch result1 = new FileNameSearch(file1, phrase);
        FileNameSearch result2 = new FileNameSearch(file2, phrase);

        assertNotEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void hashCode_ShouldReturnFalse_ForDifferentPhraseRef() {
        PdfFile file = new PdfFile("/path/to/file.pdf");
        SearchPhrase phrase1 = new SearchPhrase("test");
        SearchPhrase phrase2 = new SearchPhrase("test");

        FileNameSearch result1 = new FileNameSearch(file, phrase1);
        FileNameSearch result2 = new FileNameSearch(file, phrase2);

        assertNotEquals(result1.hashCode(), result2.hashCode());
    }
}
