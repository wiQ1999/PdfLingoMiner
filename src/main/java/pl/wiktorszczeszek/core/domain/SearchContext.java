package pl.wiktorszczeszek.core.domain;

import pl.wiktorszczeszek.core.domain.results.FileNameSearch;
import pl.wiktorszczeszek.core.domain.results.TextContentSearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SearchContext {
    private final ArrayList<PdfFile> files = new ArrayList<>();
    private final ArrayList<FileNameSearch> fileNameResults = new ArrayList<>();
    private final ArrayList<TextContentSearch> textContentResults = new ArrayList<>();
    private SearchPhrase searchPhrase = new SearchPhrase();

    public Collection<PdfFile> getFiles() {
        return Collections.unmodifiableCollection(files);
    }

    public Collection<FileNameSearch> getFileNameResults() {
        return Collections.unmodifiableCollection(fileNameResults);
    }

    public Collection<TextContentSearch> getTextContentResults() {
        return Collections.unmodifiableCollection(textContentResults);
    }

    public SearchPhrase getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(SearchPhrase value) {
        if (value == null) throw new IllegalArgumentException("Fraza wyszukiwania nie może być null.");
        searchPhrase = value;
        fileNameResults.replaceAll(tcs -> new FileNameSearch(tcs.getFile(), value));
        textContentResults.replaceAll(tcs -> new TextContentSearch(tcs.getFile(), value));
    }

    public void clearFiles() {
        files.clear();
        fileNameResults.clear();
        textContentResults.clear();
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
            if (this.files.contains(file)) continue;
            this.files.add(file);
            fileNameResults.add(new FileNameSearch(file, searchPhrase));
            textContentResults.add(new TextContentSearch(file, searchPhrase));
            added++;
        }
        return added;
    }

    public int removeFiles(PdfFile[] files) {
        if (files == null || files.length == 0) throw new IllegalArgumentException("Kolekcja plików nie może być pusta.");
        int removed = 0;
        for (PdfFile file : files) {
            if (file == null) throw new IllegalArgumentException("Plik nie może być null.");
            int fileIndex = this.files.indexOf(file);
            if (fileIndex == -1) continue;
            this.files.remove(fileIndex);
            fileNameResults.remove(fileIndex);
            textContentResults.remove(fileIndex);
            removed++;
        }
        return removed;
    }

    public void updateFileNameResult(FileNameSearch result) {
        if (result == null) throw new IllegalArgumentException("Rezultat wyszukiwania nie może być null.");
        PdfFile file = result.getFile();
        int fileIndex = files.indexOf(file);
        if (fileIndex == -1) throw new IllegalArgumentException("Rezultat wyszukiwania nie istnieje w kontekście wyszukiwania.");
        fileNameResults.set(fileIndex, result);
    }

    public void updateTextContentResult(TextContentSearch result) {
        if (result == null) throw new IllegalArgumentException("Rezultat wyszukiwania nie może być null.");
        PdfFile file = result.getFile();
        int fileIndex = files.indexOf(file);
        if (fileIndex == -1) throw new IllegalArgumentException("Rezultat wyszukiwania nie istnieje w kontekście wyszukiwania.");
        textContentResults.set(fileIndex, result);
    }
}
