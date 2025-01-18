package pl.wiktorszczeszek.core.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wiktorszczeszek.core.domain.results.ContentSummarySearch;
import pl.wiktorszczeszek.core.domain.results.FileNameSearch;
import pl.wiktorszczeszek.core.domain.results.TextContentSearch;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SearchContextTest {
    static SearchPhrase searchPhrase;
    static PdfFile pdfFile1;
    static PdfFile pdfFile2;
    static PdfFile pdfFile3;
    SearchContext searchContext;

    @BeforeAll
    static void setUp() {
        searchPhrase = new SearchPhrase("test");
        pdfFile1 = new PdfFile("/path/to/file1.pdf");
        pdfFile2 = new PdfFile("/path/to/file2.pdf");
        pdfFile3 = new PdfFile("/path/to/file3.pdf");
    }

    @BeforeEach
    void init() {
        searchContext = new SearchContext();
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
    void getContentSummaryResults_ShouldReturnUnmodifiableCollection() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        Collection<ContentSummarySearch> results = searchContext.getContentSummaryResults();
        assertThrows(UnsupportedOperationException.class, results::clear);
    }

    @Test
    void getContentSummaryResults_ShouldReturnContentSummaryResultsFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        List<ContentSummarySearch> results = new ArrayList<>(searchContext.getContentSummaryResults());
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
        assertEquals(0, searchContext.getContentSummaryResults().size());
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
        Collection<PdfFile> files = List.of(pdfFile2);

        assertEquals(1, added);
        assertFilesWithExistFiles(files);
        assertFileNameResultsWithExistFiles(files);
        assertTextContentResultsWithExistFiles(files);
        assertContentSummaryResultsWithExistFiles(files);
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
        Collection<PdfFile> files = List.of(pdfFile1, pdfFile2);

        assertEquals(2, added);
        assertFilesWithExistFiles(files);
        assertFileNameResultsWithExistFiles(files);
        assertTextContentResultsWithExistFiles(files);
        assertContentSummaryResultsWithExistFiles(files);
    }

    @Test
    void addFiles_ShouldIgnoreDuplicates() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        int added = searchContext.addFiles(new PdfFile[] {pdfFile1});
        Collection<PdfFile> files = List.of(pdfFile1);

        assertEquals(0, added);
        assertFilesWithExistFiles(files);
        assertFileNameResultsWithExistFiles(files);
        assertTextContentResultsWithExistFiles(files);
        assertContentSummaryResultsWithExistFiles(files);
    }

    @Test
    void addFiles_ShouldAddFilesToContextAndIgnoreDuplicates() {
        searchContext.addFiles(new PdfFile[] {pdfFile1});
        int added = searchContext.addFiles(new PdfFile[] {pdfFile1, pdfFile2});
        Collection<PdfFile> files = List.of(pdfFile1, pdfFile2);

        assertEquals(1, added);
        assertFilesWithExistFiles(files);
        assertFileNameResultsWithExistFiles(files);
        assertTextContentResultsWithExistFiles(files);
        assertContentSummaryResultsWithExistFiles(files);
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
        Collection<PdfFile> files = List.of(pdfFile1, pdfFile2);

        assertEquals(0, removed);
        assertFilesWithExistFiles(files);
        assertFileNameResultsWithExistFiles(files);
        assertTextContentResultsWithExistFiles(files);
        assertContentSummaryResultsWithExistFiles(files);
    }

    @Test
    void removeFiles_ShouldRemoveFilesFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2, pdfFile3});
        int removed = searchContext.removeFiles(new PdfFile[]{pdfFile2, pdfFile1});
        Collection<PdfFile> files = List.of(pdfFile3);

        assertEquals(2, removed);
        assertFilesWithExistFiles(files);
        assertFileNameResultsWithExistFiles(files);
        assertTextContentResultsWithExistFiles(files);
        assertContentSummaryResultsWithExistFiles(files);
    }

    void assertFilesWithExistFiles(Collection<PdfFile> files) {
        Collection<PdfFile> contextFiles = searchContext.getFiles();
        assertEquals(files.size(), contextFiles.size());
        for (PdfFile contextFile : contextFiles) {
            assertTrue(files.contains(contextFile));
        }
    }

    void assertFileNameResultsWithExistFiles(Collection<PdfFile> files) {
        Collection<FileNameSearch> fileNameResults = searchContext.getFileNameResults();
        assertEquals(files.size(), fileNameResults.size());
        for (FileNameSearch search : fileNameResults) {
            PdfFile searchFile = search.getFile();
            assertTrue(files.contains(searchFile));
        }
    }

    void assertTextContentResultsWithExistFiles(Collection<PdfFile> files) {
        Collection<TextContentSearch> TextContentResults =  searchContext.getTextContentResults();
        assertEquals(files.size(), TextContentResults.size());
        for (TextContentSearch search : TextContentResults) {
            PdfFile searchFile = search.getFile();
            assertTrue(files.contains(searchFile));
        }
    }

    void assertContentSummaryResultsWithExistFiles(Collection<PdfFile> files) {
        Collection<ContentSummarySearch> contentSummaryResults =  searchContext.getContentSummaryResults();
        assertEquals(files.size(), contentSummaryResults.size());
        for (ContentSummarySearch search : contentSummaryResults) {
            PdfFile searchFile = search.getFile();
            assertTrue(files.contains(searchFile));
        }
    }

    @Test
    void updateFileNameResult_ShouldThrowException_WhenSearchIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateFileNameResult(null));
    }

    @Test
    void updateFileNameResult_ShouldThrowException_WhenFileNotInContext() {
        FileNameSearch search = new FileNameSearch(pdfFile1, searchPhrase);
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateFileNameResult(search));
    }

    @Test
    void updateFileNameResult_ShouldUpdateTextContentSearchInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        FileNameSearch search = new FileNameSearch(pdfFile1, searchPhrase, true);
        searchContext.updateFileNameResult(search);

        FileNameSearch contextSearch = searchContext.getFileNameResults().stream()
                .filter(r -> r.getFile().equals(pdfFile1))
                .findFirst()
                .orElse(null);
        assertNotNull(contextSearch);
        assertEquals(search, contextSearch);
        assertTrue(contextSearch.getIsOccurrence());
    }

    @Test
    void updateTextContentResult_ShouldThrowException_WhereSearchIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateTextContentResult(null));
    }

    @Test
    void updateTextContentResult_ShouldThrowException_WhenFileNotInContext() {
        TextContentSearch search = new TextContentSearch(pdfFile1, searchPhrase);
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateTextContentResult(search));
    }

    @Test
    void updateTextContentResult_ShouldUpdateTextContentSearchInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        TextContentSearch search = new TextContentSearch(pdfFile1, searchPhrase, 5);
        searchContext.updateTextContentResult(search);

        TextContentSearch contextSearch = searchContext.getTextContentResults().stream()
                .filter(r -> r.getFile().equals(pdfFile1))
                .findFirst()
                .orElse(null);
        assertNotNull(contextSearch);
        assertEquals(search, contextSearch);
        assertEquals(5, contextSearch.getOccurrenceCount());
    }

    @Test
    void updateContentSummaryResult_ShouldThrowException_WhenSearchIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateContentSummaryResult(null));
    }

    @Test
    void updateContentSummaryResult_ShouldThrowException_WhenFileNotInContext() {
        ContentSummarySearch search = new ContentSummarySearch(pdfFile1);
        assertThrows(IllegalArgumentException.class, () -> searchContext.updateContentSummaryResult(search));
    }

    @Test
    void updateContentSummaryResult_ShouldUpdateTextContentSearchInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        ContentSummarySearch search = new ContentSummarySearch(pdfFile1, "Summary test.");
        searchContext.updateContentSummaryResult(search);

        ContentSummarySearch contextSearch = searchContext.getContentSummaryResults().stream()
                .filter(r -> r.getFile().equals(pdfFile1))
                .findFirst()
                .orElse(null);
        assertNotNull(contextSearch);
        assertEquals(search, contextSearch);
        assertTrue(contextSearch.getIsSearched());
    }
}