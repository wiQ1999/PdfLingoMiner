package pl.wiktorszczeszek.core.domain.results;

import pl.wiktorszczeszek.core.domain.PdfFile;
import pl.wiktorszczeszek.core.domain.SearchPhrase;

import java.util.Objects;

public class FileNameSearch implements Comparable<FileNameSearch> {
    private final PdfFile file;
    private final SearchPhrase phrase;
    private boolean isOccurrence;
    private boolean isSearched;

    public FileNameSearch(PdfFile file, SearchPhrase phrase) {
        if (file == null) throw new IllegalArgumentException("Plik nie może być null.");
        if (phrase == null) throw new IllegalArgumentException("Fraza wyszukiwania nie może być null");
        this.file = file;
        this.phrase = phrase;
    }

    public FileNameSearch(PdfFile file, SearchPhrase phrase, boolean isOccurrence) {
        this(file, phrase);
        this.isOccurrence = isOccurrence;
        isSearched = true;
    }

    public PdfFile getFile() {
        return file;
    }

    public SearchPhrase getPhrase() {
        return phrase;
    }

    public boolean getIsOccurrence() {
        return isOccurrence;
    }

    public boolean getIsSearched() {
        return isSearched;
    }

    public void setIsOccurrence() {
        setIsOccurrence(true);
    }

    public void setIsOccurrence(boolean isOccurrence) {
        this.isOccurrence = isOccurrence;
        isSearched = true;
    }

    public void reset() {
        isOccurrence = false;
        isSearched = false;
    }

    @Override
    public int compareTo(FileNameSearch o) {
        return file.compareTo(o.getFile());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FileNameSearch other)) return false;
        return this.file.equals(other.file) && this.phrase.equals(other.phrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, phrase);
    }

    @Override
    public String toString() {
        return "FileNameSearch{" +
                "file=" + file +
                ", phrase=" + phrase +
                ", isOccurrenceCount=" + isOccurrence +
                ", isSearched=" + isSearched +
                '}';
    }
}
