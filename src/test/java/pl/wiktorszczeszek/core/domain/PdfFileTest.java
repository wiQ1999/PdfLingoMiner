package pl.wiktorszczeszek.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PdfFileTest {
    @Test
    void constructor_ShouldThrowException_WhenPathIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PdfFile(null);
        });
    }

    @Test
    void constructor_ShouldThrowException_WhenPathIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PdfFile(" ");
        });
    }

    @Test
    void getPath_ShouldReturnTrimmedPath() {
        PdfFile pdfFile = new PdfFile("   /path/to/file.pdf   ");
        assertEquals("/path/to/file.pdf", pdfFile.path());
    }

    @Test
    void equals_ShouldReturnTrue_ForSamePath() {
        PdfFile pdfFile1 = new PdfFile("/path/to/file1.pdf");
        PdfFile pdfFile2 = new PdfFile("/PATH/TO/FILE1.pdf");
        assertEquals(pdfFile1, pdfFile2);
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentPaths() {
        PdfFile pdfFile1 = new PdfFile("/path/to/file1.pdf");
        PdfFile pdfFile2 = new PdfFile("/PATH/TO/FILE2.pdf");
        assertNotEquals(pdfFile1, pdfFile2);
    }

    @Test
    void hashCode_ShouldReturnTrue_ForSamePath() {
        PdfFile pdfFile1 = new PdfFile("/path/to/file1.pdf");
        PdfFile pdfFile2 = new PdfFile("/PATH/TO/FILE1.pdf");
        assertEquals(pdfFile1.hashCode(), pdfFile2.hashCode());
    }

    @Test
    void hashCode_ShouldReturnFalse_ForDifferentPath() {
        PdfFile pdfFile1 = new PdfFile("/path/to/file1.pdf");
        PdfFile pdfFile2 = new PdfFile("/PATH/TO/FILE2.pdf");
        assertNotEquals(pdfFile1.hashCode(), pdfFile2.hashCode());
    }
}
