package pl.wiktorszczeszek.core.domain.results;

import org.junit.jupiter.api.Test;
import pl.wiktorszczeszek.core.domain.PdfFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContentSummarySearchTest {
    final String fileName = "/path/to/file.pdf";
    final PdfFile file = new PdfFile(fileName);
    final String summary = "Streszczenie zawartości pliku file.pdf.";

    @Test
    void constructor_ShouldThrowException_WhenFileIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ContentSummarySearch(null));
        assertThrows(IllegalArgumentException.class, () -> new ContentSummarySearch(null, summary));
    }

    @Test
    void constructor_ShouldThrowException_WhenSummaryIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ContentSummarySearch(file, null));
    }

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        ContentSummarySearch result1 = new ContentSummarySearch(file);
        ContentSummarySearch result2 = new ContentSummarySearch(file, summary);

        assertEquals(file, result1.getFile());
        assertEquals("", result1.getSummary());
        assertFalse(result1.getIsSearched());

        assertEquals(file, result2.getFile());
        assertEquals(summary, result2.getSummary());
        assertTrue(result2.getIsSearched());
    }

    @Test
    void getFile_ShouldReturnsFile() {
        ContentSummarySearch result = new ContentSummarySearch(file);
        assertEquals(file, result.getFile());
    }

    @Test
    void getSummary_ShouldReturnsSummary() {
        ContentSummarySearch result = new ContentSummarySearch(file, summary);
        assertEquals(summary, result.getSummary());
    }

    @Test
    void getIsSearched_ShouldReturnsNotFalse_ForNonInitializeSummary() {
        ContentSummarySearch result = new ContentSummarySearch(file);
        assertFalse(result.getIsSearched());
    }

    @Test
    void getIsSearched_ShouldReturnsTrue_ForInitializeSummary() {
        ContentSummarySearch result = new ContentSummarySearch(file, summary);
        assertTrue(result.getIsSearched());
    }

    @Test
    void getIsSearched_ShouldReturnsTrue_ForNonInitializeSummaryButSet() {
        ContentSummarySearch result = new ContentSummarySearch(file);
        result.setSummary(summary);
        assertTrue(result.getIsSearched());
    }

    @Test
    void getContainsSummary_ShouldReturnsFalse_WhenNonSummary() {
        ContentSummarySearch result = new ContentSummarySearch(file);
        assertFalse(result.getContainsSummary());
    }

    @Test
    void getContainsSummary_ShouldReturnsFalse_WhenIsSummary() {
        ContentSummarySearch result = new ContentSummarySearch(file, summary);
        assertTrue(result.getContainsSummary());
    }

    @Test
    void setSummary_ShouldThrowIllegalArgumentException_WhenSummaryIsNull() {
        ContentSummarySearch result = new ContentSummarySearch(file);
        assertThrows(IllegalArgumentException.class, () -> result.setSummary(null));
    }

    @Test
    void setSummary_ShouldSetSummaryAndSetIsSearched() {
        ContentSummarySearch result = new ContentSummarySearch(file);

        assertEquals("", result.getSummary());
        assertFalse(result.getIsSearched());

        result.setSummary(summary);

        assertEquals(summary, result.getSummary());
        assertTrue(result.getIsSearched());
    }

    @Test
    void compareTo_ShouldReturnNegative_WhenFileBeginsWithEarlierLetter() {
        PdfFile file1 = new PdfFile("1");
        PdfFile file2 = new PdfFile("2");
        ContentSummarySearch result1 = new ContentSummarySearch(file1);
        ContentSummarySearch result2 = new ContentSummarySearch(file2);

        assertTrue(result1.compareTo(result2) < 0);
    }

    @Test
    void compareTo_ShouldReturnZero_WhenEqualFile() {
        PdfFile file1 = new PdfFile(fileName);
        PdfFile file2 = new PdfFile(fileName);
        ContentSummarySearch result1 = new ContentSummarySearch(file1);
        ContentSummarySearch result2 = new ContentSummarySearch(file2);

        assertEquals(0, result1.compareTo(result2));
    }

    @Test
    void compareTo_ShouldReturnNegative_WhenFileBeginsWithLaterLetter() {
        PdfFile file1 = new PdfFile("2");
        PdfFile file2 = new PdfFile("1");
        ContentSummarySearch result1 = new ContentSummarySearch(file1);
        ContentSummarySearch result2 = new ContentSummarySearch(file2);

        assertTrue(result1.compareTo(result2) > 0);
    }

    @Test
    void equals_ShouldReturnTrue_ForEqualFileAndEqualSummary() {
        PdfFile file1 = new PdfFile(fileName);
        PdfFile file2 = new PdfFile(fileName);
        ContentSummarySearch result1 = new ContentSummarySearch(file1, summary);
        ContentSummarySearch result2 = new ContentSummarySearch(file2, summary);

        assertEquals(result1, result2);
    }

    @Test
    void equals_ShouldReturnFalse_ForNonEqualFileAndEqualSummary() {
        PdfFile file1 = new PdfFile("/path/to/file1.pdf");
        PdfFile file2 = new PdfFile("/path/to/file2.pdf");
        ContentSummarySearch result1 = new ContentSummarySearch(file1, summary);
        ContentSummarySearch result2 = new ContentSummarySearch(file2, summary);

        assertNotEquals(result1, result2);
    }

    @Test
    void equals_ShouldReturnFalse_ForEqualFileAndNonEqualSummary() {
        PdfFile file1 = new PdfFile(fileName);
        PdfFile file2 = new PdfFile(fileName);
        ContentSummarySearch result1 = new ContentSummarySearch(file1, "Streszczenie1");
        ContentSummarySearch result2 = new ContentSummarySearch(file2, "Streszczenie2");

        assertNotEquals(result1, result2);
    }

    @Test
    void hashCode_ShouldReturnTrue_ForEqualFileAndEqualSummary() {
        PdfFile file1 = new PdfFile(fileName);
        PdfFile file2 = new PdfFile(fileName);
        ContentSummarySearch result1 = new ContentSummarySearch(file1, summary);
        ContentSummarySearch result2 = new ContentSummarySearch(file2, summary);

        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void hashCode_ShouldReturnFalse_ForNonEqualFileAndEqualSummary() {
        PdfFile file1 = new PdfFile("/path/to/file1.pdf");
        PdfFile file2 = new PdfFile("/path/to/file2.pdf");
        ContentSummarySearch result1 = new ContentSummarySearch(file1, summary);
        ContentSummarySearch result2 = new ContentSummarySearch(file2, summary);

        assertNotEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void hashCode_ShouldReturnFalse_ForEqualFileAndNonEqualSummary() {
        PdfFile file1 = new PdfFile(fileName);
        PdfFile file2 = new PdfFile(fileName);
        ContentSummarySearch result1 = new ContentSummarySearch(file1, "Streszczenie1");
        ContentSummarySearch result2 = new ContentSummarySearch(file2, "Streszczenie2");

        assertNotEquals(result1.hashCode(), result2.hashCode());
    }
}
