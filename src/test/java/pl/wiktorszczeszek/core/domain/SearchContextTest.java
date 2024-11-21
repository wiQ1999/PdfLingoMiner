package pl.wiktorszczeszek.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(UnsupportedOperationException.class, () -> {
            files.clear();
        });
    }

    @Test
    void getFiles_ShouldReturnFilesFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(2, files.size());
    }

    @Test
    void getResults_ShouldReturnUnmodifiableCollection() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        Collection<SearchResult> results = searchContext.getResults();
        assertThrows(UnsupportedOperationException.class, () -> {
            results.clear();
        });
    }

    @Test
    void getResult_ShouldReturnResultsFromContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        Collection<SearchResult> results = searchContext.getResults();
        assertEquals(2, results.size());
    }

    @Test
    void getSearchPhrase_ShouldReturnNotValidPhrase_WhenContextIsInitialized() {
        assertFalse(searchContext.getSearchPhrase().isValid());
    }

    @Test
    void getSearchPhrase_ShouldReturnValidPhrase_WhenValidhraseIsSet() {
        searchContext.setSearchPhrase(new SearchPhrase("example"));
        assertTrue(searchContext.getSearchPhrase().isValid());
    }

    @Test
    void setSearchPhrase_ShouldThrowException_WhenSearchPhraseIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.setSearchPhrase(null);
        });
    }

    @Test
    void setSearchPhrase_ShouldUpdateSearchPhraseInContext() {
        SearchPhrase newPhrase = new SearchPhrase("example");
        searchContext.setSearchPhrase(newPhrase);
        assertEquals(newPhrase, searchContext.getSearchPhrase());
    }

    @Test
    void setSearchPhrase_ShouldUpdateAllSearchResults() {
        PdfFile[] files = new PdfFile[]{pdfFile1, pdfFile2};
        searchContext.addFiles(files);
        SearchPhrase newPhrase = new SearchPhrase("example");
        searchContext.setSearchPhrase(newPhrase);

        Arrays.sort(files);
        Object[] results = searchContext.getResults().toArray();
        Arrays.sort(results);
        for (int i = 0; i < files.length; i++) {
            PdfFile file = files[i];
            SearchResult result = (SearchResult)results[i];
            assertEquals(newPhrase, result.getPhrase());
            assertEquals(file, result.getFile());
        }
    }

    @Test
    void clearFiles_ShouldClearAllFiles() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});

        searchContext.clearFiles();
        assertEquals(0, searchContext.getFiles().size());
        assertEquals(0, searchContext.getResults().size());
    }

    @Test
    void clearAndSetFiles_ShouldThrowException_WhenFilesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.clearAndSetFiles(null);
        });
    }

    @Test
    void clearAndSetFiles_ShouldThrowException_WhenFilesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.clearAndSetFiles(new PdfFile[] {});
        });
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

        Collection<SearchResult> results = searchContext.getResults();
        assertEquals(1, results.size());
        Collection<PdfFile> resultFiles = results.stream().map(SearchResult::getFile).toList();
        assertFalse(resultFiles.contains(pdfFile1));
        assertTrue(resultFiles.contains(pdfFile2));
    }

    @Test
    void addFiles_ShouldThrowException_WhenFilesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.addFiles(null);
        });
    }

    @Test
    void addFiles_ShouldThrowException_WhenFilesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.addFiles(new PdfFile[] {});
        });
    }

    @Test
    void addFiles_ShouldAddFilesToContext() {
        int added = searchContext.addFiles(new PdfFile[] {pdfFile1, pdfFile2});
        assertEquals(2, added);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(2, files.size());
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<SearchResult> results = searchContext.getResults();
        assertEquals(2, results.size());
        Collection<PdfFile> resultFiles = results.stream().map(SearchResult::getFile).toList();
        assertTrue(resultFiles.contains(pdfFile1));
        assertTrue(resultFiles.contains(pdfFile2));
    }

    @Test
    void addFiles_ShouldIgnoreDuplicates() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        int added = searchContext.addFiles(new PdfFile[] {pdfFile1});
        assertEquals(0, added);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(1, files.size());

        Collection<SearchResult> results = searchContext.getResults();
        assertEquals(1, results.size());
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

        Collection<SearchResult> results = searchContext.getResults();
        assertEquals(2, results.size());
        Collection<PdfFile> resultFiles = results.stream().map(SearchResult::getFile).toList();
        assertTrue(resultFiles.contains(pdfFile1));
        assertTrue(resultFiles.contains(pdfFile2));
    }

    @Test
    void removeFiles_ShouldThrowException_WhenFilesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.removeFiles(null);
        });
    }

    @Test
    void removeFiles_ShouldThrowException_WhenFilesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.removeFiles(new PdfFile[] {});
        });
    }

    @Test
    void removeFiles_ShouldNotThrowException_WhenFileNotInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1, pdfFile2});
        int removed = searchContext.removeFiles(new PdfFile[]{pdfFile3});
        assertEquals(0, removed);

        Collection<PdfFile> files = searchContext.getFiles();
        assertEquals(2, files.size());
        assertTrue(files.contains(pdfFile1));
        assertTrue(files.contains(pdfFile2));

        Collection<SearchResult> results = searchContext.getResults();
        assertEquals(2, results.size());
        Collection<PdfFile> resultFiles = results.stream().map(SearchResult::getFile).toList();
        assertTrue(resultFiles.contains(pdfFile1));
        assertTrue(resultFiles.contains(pdfFile2));
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

        Collection<SearchResult> results = searchContext.getResults();
        assertEquals(1, results.size());
        Collection<PdfFile> resultFiles = results.stream().map(SearchResult::getFile).toList();
        assertFalse(resultFiles.contains(pdfFile1));
        assertFalse(resultFiles.contains(pdfFile2));
        assertTrue(resultFiles.contains(pdfFile3));
    }

    @Test
    void updateSearchResult_ShouldThrowException_WhenResultIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.updateSearchResult(null);
        });
    }

    @Test
    void updateSearchResult_ShouldThrowException_WhenFileNotInContext() {
        SearchResult result = new SearchResult(pdfFile1, searchPhrase);
        assertThrows(IllegalArgumentException.class, () -> {
            searchContext.updateSearchResult(result);
        });
    }

    @Test
    void updateSearchResult_ShouldUpdateResultInContext() {
        searchContext.addFiles(new PdfFile[]{pdfFile1});
        SearchResult result = new SearchResult(pdfFile1, searchPhrase, 5);
        searchContext.updateSearchResult(result);

        SearchResult updatedResult = searchContext.getResults().stream()
                .filter(r -> r.getFile().equals(pdfFile1))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedResult);
        assertEquals(5, updatedResult.getOccurrenceCount());
    }
}