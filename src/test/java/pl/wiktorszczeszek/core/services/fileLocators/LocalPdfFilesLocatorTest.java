package pl.wiktorszczeszek.core.services.fileLocators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.wiktorszczeszek.core.domain.PdfFile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class LocalPdfFilesLocatorTest {
    @Test
    void isExist_ShouldReturnFalse_WhenPdfNotExist(@TempDir Path tempDir) {
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        Path path = tempDir.resolve("notExist.pdf");
        boolean isExist = locator.isExist(path.toString());
        assertFalse(isExist);
    }

    @Test
    void isExist_ShouldReturnTrue_WhenPdfExist(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempFile(tempDir, "exist", ".pdf");
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        boolean isExist = locator.isExist(path.toString());
        assertTrue(isExist);
    }

    @Test
    void isExist_ShouldReturnTrue_WhenPdfExistWithUpperCase(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempFile(tempDir, "EXIST", ".PDF");
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        boolean isExist = locator.isExist(path.toString());
        assertTrue(isExist);
    }

    @Test
    void isExist_ShouldReturnTrue_When2PdfExists(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempFile(tempDir, "exist", ".pdf");
        Files.createTempFile(tempDir, "other1", ".txt");
        Files.createTempFile(tempDir, "other2", ".docx");
        Files.createTempFile(tempDir, "other3", ".png");
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        boolean isExist = locator.isExist(path.toString());
        assertTrue(isExist);
    }

    @Test
    void isExist_ShouldReturnTrue_WhenPdfExistAndOtherFiles(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempFile(tempDir, "EXIST", ".PDF");
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        boolean isExist = locator.isExist(path.toString());
        assertTrue(isExist);
    }

    @Test
    void isExist_ShouldReturnTrue_WhenPdfExistIn1SubDir(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempDirectory(tempDir, "sub1");
        path = Files.createTempFile(path, "exist", ".pdf");
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        boolean isExist = locator.isExist(path.toString());
        assertTrue(isExist);
    }

    @Test
    void isExist_ShouldReturnTrue_WhenPdfExistIn2SubDir(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempDirectory(tempDir, "sub1");
        path = Files.createTempDirectory(path, "sub2");
        path = Files.createTempFile(path, "exist", ".pdf");
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        boolean isExist = locator.isExist(path.toString());
        assertTrue(isExist);
    }

    @Test
    void locateFiles_shouldReturnEmptyCollection_whenPdfNotExist(@TempDir Path tempDir) {
        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        Collection<PdfFile> files = locator.locateFiles(tempDir.toString());

        assertTrue(files.isEmpty());
    }

    @Test
    void locateFiles_shouldReturn1PdfFileCollection_when1PdfExist(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempFile(tempDir, "exist", ".pdf");

        PdfFile pdf1 = new PdfFile(path.toString());

        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        Collection<PdfFile> files = locator.locateFiles(tempDir.toString());

        assertEquals(1, files.size());
        assertTrue(files.contains(pdf1));
    }

    @Test
    void locateFiles_shouldReturn2PdfFileCollection_when2PdfExistAndOtherFiles(@TempDir Path tempDir) throws IOException {
        Path path1 = Files.createTempFile(tempDir, "exist1", ".pdf");
        Path path2 = Files.createTempFile(tempDir, "exist2", ".pdf");
        Files.createTempFile(tempDir, "other1", ".txt");
        Files.createTempFile(tempDir, "other2", ".docx");
        Files.createTempFile(tempDir, "other3", ".png");

        PdfFile pdf1 = new PdfFile(path1.toString());
        PdfFile pdf2 = new PdfFile(path2.toString());

        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        Collection<PdfFile> files = locator.locateFiles(tempDir.toString());

        assertEquals(2, files.size());
        assertTrue(files.contains(pdf1));
        assertTrue(files.contains(pdf2));
    }

    @Test
    void locateFiles_shouldReturn2PdfFileCollection_when2PdfExistAndOtherFilesBetween2SubDir(@TempDir Path tempDir) throws IOException {
        Files.createTempFile(tempDir, "other1", ".txt");

        Path sub1 = Files.createTempDirectory(tempDir, "sub1");
        Path path1 = Files.createTempFile(sub1, "exist1", ".pdf");
        Files.createTempFile(sub1, "other2", ".docx");

        Path sub2 = Files.createTempDirectory(sub1, "sub2");
        Files.createTempFile(sub2, "other3", ".png");
        Path path2 = Files.createTempFile(sub2, "exist2", ".pdf");

        PdfFile pdf1 = new PdfFile(path1.toString());
        PdfFile pdf2 = new PdfFile(path2.toString());

        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        Collection<PdfFile> files = locator.locateFiles(tempDir.toString());

        assertEquals(2, files.size());
        assertTrue(files.contains(pdf1));
        assertTrue(files.contains(pdf2));
    }

    @Test
    void locateFiles_shouldReturnTempPdfFileInCollection_whenPdfExistAndPerformSearchInDrive(@TempDir Path tempDir) throws IOException {
        Path path = Files.createTempFile(tempDir, "exist", ".pdf");

        PdfFile pdf1 = new PdfFile(path.toString());
        path = getRootPath(path);

        LocalPdfFilesLocator locator = new LocalPdfFilesLocator();
        Collection<PdfFile> files = locator.locateFiles(path.toString());

        assertFalse(files.isEmpty());
        assertTrue(files.contains(pdf1));
    }

    private Path getRootPath(Path path) {
        Path root = path.getRoot();

        if (root == null) {
            root = path.toAbsolutePath().getRoot();
        }

        return root;
    }
}
