package pl.wiktorszczeszek.core.domain;

import java.util.Objects;

public class SearchResult implements Comparable<SearchResult> {
    private final PdfFile file;
    private final SearchPhrase phrase;
    private int occurrenceCount;
    private boolean isSearched;

    public SearchResult(PdfFile file, SearchPhrase phrase) {
        if (file == null) throw new IllegalArgumentException("Plik nie może być null.");
        if (phrase == null) throw new IllegalArgumentException("Fraza wyszukiwania nie może być null.");
        this.file = file;
        this.phrase = phrase;
    }

    public SearchResult(PdfFile file, SearchPhrase phrase, int occurrenceCount) {
        this(file, phrase);
        if (occurrenceCount < 0) throw new IllegalArgumentException("Liczba wystąpień nie może być ujemna.");
        this.occurrenceCount = occurrenceCount;
        isSearched = true;
    }

    public PdfFile getFile() {
        return file;
    }

    public SearchPhrase getPhrase() {
        return phrase;
    }

    public int getOccurrenceCount() {
        return occurrenceCount;
    }

    public boolean getIsSearched() {
        return isSearched;
    }

    public void addOccurrence() {
        occurrenceCount++;
        isSearched = true;
    }

    public void addOccurrence(int number) {
        if (number < 0) throw new IllegalArgumentException("Liczba wystąpień nie może być ujemna.");
        occurrenceCount += number;
        isSearched = true;
    }

    public void reset() {
        occurrenceCount = 0;
        isSearched = false;
    }

    @Override
    public int compareTo(SearchResult o) {
        return file.compareTo(o.getFile());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SearchResult other)) return false;
        return this.file.equals(other.file) && this.phrase.equals(other.phrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, phrase);
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "file=" + file +
                ", phrase=" + phrase +
                ", occurrenceCount=" + occurrenceCount +
                ", isSearched=" + isSearched +
                '}';
    }
}
