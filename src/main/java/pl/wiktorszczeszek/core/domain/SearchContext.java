package pl.wiktorszczeszek.core.domain;

import java.util.*;

public class SearchContext {
    private final Map<PdfFile, SearchResult> results = new TreeMap<>();
    private SearchPhrase searchPhrase = new SearchPhrase();

    public Collection<PdfFile> getFiles() {
        return Collections.unmodifiableSet(results.keySet());
    }

    public Collection<SearchResult> getResults() {
        return Collections.unmodifiableCollection(results.values());
    }

    public SearchPhrase getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(SearchPhrase value) {
        if (value == null) throw new IllegalArgumentException("Fraza wyszukiwania nie może być null.");
        searchPhrase = value;
        results.replaceAll((f, _) -> new SearchResult(f, value));
    }

    public void clearFiles() {
        results.clear();
    }

    public int clearAndSetFiles(PdfFile[] files) {
        if (files == null || files.length == 0) throw new IllegalArgumentException("Kolekcja plików nie może być pusta.");
        clearFiles();
        return addFiles(files);
    }

    public int addFiles(PdfFile[] files) {
        if (files == null || files.length == 0) throw new IllegalArgumentException("Kolekcja plików nie może być pusta.");
        int added = 0;
        for (PdfFile file : files) {
            if (file == null) throw new IllegalArgumentException("Plik nie może być null.");
            if (results.containsKey(file)) continue;
            results.put(file, new SearchResult(file, searchPhrase));
            added++;
        }
        return added;
    }

    public int removeFiles(PdfFile[] files) {
        if (files == null || files.length == 0) throw new IllegalArgumentException("Kolekcja plików nie może być pusta.");
        int removed = 0;
        for (PdfFile file : files) {
            if (file == null) throw new IllegalArgumentException("Plik nie może być null.");
            if (results.remove(file) != null) {
                removed++;
            }
        }
        return removed;
    }

    public void updateSearchResult(SearchResult result) {
        if (result == null) throw new IllegalArgumentException("Rezultat wyszukiwania nie może być null.");
        PdfFile file = result.getFile();
        if (!results.containsKey(file)) throw new IllegalArgumentException("Rezultat wyszukiwania nie istnieje w kontekście wyszukiwania.");
        results.put(file, result);
    }
}
