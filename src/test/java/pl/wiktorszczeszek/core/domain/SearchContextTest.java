package pl.wiktorszczeszek.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wiktorszczeszek.core.domain.results.FileNameSearch;
import pl.wiktorszczeszek.core.domain.results.TextContentSearch;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SearchContextTest {
    private SearchPhrase searchPhrase;
    private SearchContext searchContext;
    private PdfFile pdfFile1;
    private PdfFile pdfFile2;
    private PdfFile pdfFile3;

    @BeforeEach
    void setUp() {
        searchPhrase = new SearchPhrase("test");
        searchContext = new SearchContext();
        pdfFile1 = new PdfFile("/path/to/file1.pdf");
        pdfFile2 = new PdfFile("/path/to/file2.pdf");
        pdfFile3 = new PdfFile("/path/to/file3.pdf");
    }

    @Test
    void getFiles_ShouldReturnUnmodifiableCollection() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        Collection<PdfFile> files = searchContext.getFiles();
        assertThrows(UnsupportedOperationException.class, files::clear);
    }

    @Test
    void getFiles_ShouldReturnFilesFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        List<PdfFile> files = new ArrayList<>(searchContext.getFiles());
        assertEquals(2, files.size());
        assertEquals(pdfFile1, files.get(0));
        assertEquals(pdfFile2, files.get(1));
    }

    @Test
    void getFileNameResults_ShouldReturnUnmodifiableCollection() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        Collection<FileNameSearch> results = searchContext.getFileNameResults();
        assertThrows(UnsupportedOperationException.class, results::clear);
    }

    @Test
    void getFileNameResults_ShouldReturnFileNameResultsFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        List<FileNameSearch> results = new ArrayList<>(searchContext.getFileNameResults());
        assertEquals(2, results.size());
        assertEquals(pdfFile1, results.get(0).getFile());
        assertEquals(pdfFile2, results.get(1).getFile());
    }

    @Test
    void getTextContentResults_ShouldReturnUnmodifiableCollection() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        Collection<TextContentSearch> results = searchContext.getTextContentResults();
        assertThrows(UnsupportedOperationException.class, results::clear);
    }

    @Test
    void getTextContentResults_ShouldReturnTextContentResultsFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        List<TextContentSearch> results = new ArrayList<>(searchContext.getTextContentResults());
        assertEquals(2, results.size());
        assertEquals(pdfFile1, results.get(0).getFile());
        assertEquals(pdfFile2, results.get(1).getFile());
    }

    @Test
    void getSearchPhrase_ShouldReturnNotValidPhrase_WhenContextIsInitialized() {
        assertFalse(searchContext.getSearchPhrase().isValid());
    }

    @Test
    void getSearchPhrase_ShouldReturnValidPhrase_WhenValidPhraseIsSet() {
        searchContext.setSearchPhrase(new SearchPhrase("example"));
        assertTrue(searchContext.getSearchPhrase().isValid());
    }

    @Test
    void setSearchPhrase_ShouldThrowException_WhenSearchPhraseIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.setSearchPhrase(null));
    }

    @Test
    void setSearchPhrase_ShouldUpdateSearchPhraseInContext() {
        SearchPhrase newPhrase = new SearchPhrase("example");
        searchContext.setSearchPhrase(newPhrase);
        assertEquals(newPhrase, searchContext.getSearchPhrase());
    }

    @Test
    void setSearchPhrase_ShouldUpdateAllSearchResults() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        SearchPhrase newPhrase = new SearchPhrase("example");
        searchContext.setSearchPhrase(newPhrase);

        ArrayList<PdfFile> existFiles = new ArrayList<>(searchContext.getFiles());
        ArrayList<FileNameSearch> fileNameResults = new ArrayList<>(searchContext.getFileNameResults());
        ArrayList<TextContentSearch> textContentResults = new ArrayList<>(searchContext.getTextContentResults());

        for (int i = 0; i < existFiles.size(); i++) {
            PdfFile file = existFiles.get(i);
            FileNameSearch fileName = fileNameResults.get(i);
            TextContentSearch textContent = textContentResults.get(i);

            assertEquals(file, fileName.getFile());
            assertEquals(file, textContent.getFile());
            assertEquals(newPhrase, fileName.getPhrase());
            assertEquals(newPhrase, textContent.getPhrase());
        }
    }

    @Test
    void clearFiles_ShouldClearAllFiles() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});

        searchContext.clearFiles();
        assertEquals(0, searchContext.getFiles().size());
        assertEquals(0, searchContext.getTextContentResults().size());
    }

    @Test
    void clearAndSetFiles_ShouldThrowException_WhenFilesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.clearAndSetFiles(null));
    }

    @Test
    void clearAndSetFiles_ShouldThrowException_WhenFilesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.clearAndSetFiles(new PdfFile[] {}));
    }

    @Test
    void clearAndSetFiles_ShouldReplaceAllFiles() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        int added = searchContext.clearAndSetFiles(new PdfFile[]{pdfFile2});
        assertEquals(1, added);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(1, files.size());
        assertFalse(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<FileNameSearch> fileNameResults = searchContext.getFileNameResults();
        assertEquals(1, fileNameResults.size());
        files = fileNameResults.stream().map(FileNameSearch::getFile).toList();
        assertFalse(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<TextContentSearch> textContentResults = searchContext.getTextContentResults();
        assertEquals(1, textContentResults.size());
        files = textContentResults.stream().map(TextContentSearch::getFile).toList();
        assertFalse(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));
    }

    @Test
    void addFiles_ShouldThrowException_WhenFilesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.addFiles(null));
    }

    @Test
    void addFiles_ShouldThrowException_WhenFilesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.addFiles(new PdfFile[] {}));
    }

    @Test
    void addFiles_ShouldAddFilesToContext() {
        int added = searchContext.addFiles(new PdfFile[] {pdfFile1, pdfFile2});
        assertEquals(2, added);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(2, files.size());
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<FileNameSearch> fileNameResult = searchContext.getFileNameResults();
        assertEquals(2, fileNameResult.size());
        files = fileNameResult.stream().map(FileNameSearch::getFile).toList();
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<TextContentSearch> textContentResults = searchContext.getTextContentResults();
        assertEquals(2, textContentResults.size());
        files = textContentResults.stream().map(TextContentSearch::getFile).toList();
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));
    }

    @Test
    void addFiles_ShouldIgnoreDuplicates() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        int added = searchContext.addFiles(new PdfFile[] {pdfFile1});
        assertEquals(0, added);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(1, files.size());

        Collection<FileNameSearch> fileNameResults = searchContext.getFileNameResults();
        assertEquals(1, fileNameResults.size());

        Collection<TextContentSearch> textContentResults = searchContext.getTextContentResults();
        assertEquals(1, textContentResults.size());
    }

    @Test
    void addFiles_ShouldAddFilesToContextAndIgnoreDuplicates() {
        searchContext.addFiles(new PdfFile[] {pdfFile1});
        int added = searchContext.addFiles(new PdfFile[] {pdfFile1, pdfFile2});
        assertEquals(1, added);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(2, files.size());
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<FileNameSearch> fileNameResults = searchContext.getFileNameResults();
        assertEquals(2, fileNameResults.size());
        files = fileNameResults.stream().map(FileNameSearch::getFile).toList();
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<TextContentSearch> textContentResults = searchContext.getTextContentResults();
        assertEquals(2, textContentResults.size());
        files = textContentResults.stream().map(TextContentSearch::getFile).toList();
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));
    }

    @Test
    void removeFiles_ShouldThrowException_WhenFilesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.removeFiles(null));
    }

    @Test
    void removeFiles_ShouldThrowException_WhenFilesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.removeFiles(new PdfFile[] {}));
    }

    @Test
    void removeFiles_ShouldNotRemoveAnyFile_WhenFileNotInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        int removed = searchContext.removeFiles(new PdfFile[]{pdfFile3});
        assertEquals(0, removed);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(2, files.size());
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<FileNameSearch> fileNameResults = searchContext.getFileNameResults();
        assertEquals(2, fileNameResults.size());
        files = fileNameResults.stream().map(FileNameSearch::getFile).toList();
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<TextContentSearch> textContentResults = searchContext.getTextContentResults();
        assertEquals(2, textContentResults.size());
        files = textContentResults.stream().map(TextContentSearch::getFile).toList();
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));
    }

    @Test
    void removeFiles_ShouldRemoveFilesFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2, pdfFile3});
        int removed = searchContext.removeFiles(new PdfFile[]{pdfFile2, pdfFile1});
        assertEquals(2, removed);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(1, files.size());
        assertFalse(files.contains(pdfFile1));
        assertFalse(files.contains(pdfFile2));
        assertTrue(files.contains(pdfFile3));

        Collection<FileNameSearch> fileNameResults = searchContext.getFileNameResults();
        assertEquals(1, fileNameResults.size());
        files = fileNameResults.stream().map(FileNameSearch::getFile).toList();
        assertFalse(files.contains(pdfFile1));
        assertFalse(files.contains(pdfFile2));
        assertTrue(files.contains(pdfFile3));

        Collection<TextContentSearch> textContentResults = searchContext.getTextContentResults();
        assertEquals(1, textContentResults.size());
        files = textContentResults.stream().map(TextContentSearch::getFile).toList();
        assertFalse(files.contains(pdfFile1));
        assertFalse(files.contains(pdfFile2));
        assertTrue(files.contains(pdfFile3));
    }

    @Test
    void updateFileNameResult_ShouldThrowException_WhenResultIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateFileNameResult(null));
    }

    @Test
    void updateFileNameResult_ShouldThrowException_WhenFileNotInContext() {
        FileNameSearch result = new FileNameSearch(pdfFile1, searchPhrase);
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateFileNameResult(result));
    }

    @Test
    void updateFileNameResult_ShouldUpdateTextContentResultsInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        FileNameSearch result = new FileNameSearch(pdfFile1, searchPhrase, true);
        searchContext.updateFileNameResult(result);

        FileNameSearch fileName = searchContext.getFileNameResults().stream()
                .filter(r -> r.getFile().equals(pdfFile1))
                .findFirst()
                .orElse(null);
        assertNotNull(fileName);
        assertTrue(fileName.getIsOccurrence());
    }

    @Test
    void updateTextContentResult_ShouldThrowException_WhenResultIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateTextContentResult(null));
    }

    @Test
    void updateTextContentResult_ShouldThrowException_WhenFileNotInContext() {
        TextContentSearch result = new TextContentSearch(pdfFile1, searchPhrase);
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateTextContentResult(result));
    }

    @Test
    void updateTextContentResult_ShouldUpdateTextContentResultsInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        TextContentSearch result = new TextContentSearch(pdfFile1, searchPhrase, 5);
        searchContext.updateTextContentResult(result);

        TextContentSearch textContent = searchContext.getTextContentResults().stream()
                .filter(r -> r.getFile().equals(pdfFile1))
                .findFirst()
                .orElse(null);
        assertNotNull(textContent);
        assertEquals(5, textContent.getOccurrenceCount());
    }
}