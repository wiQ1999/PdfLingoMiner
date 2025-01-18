package pl.wiktorszczeszek.core.domain.results;

import pl.wiktorszczeszek.core.domain.PdfFile;

import java.util.Objects;

public class ContentSummarySearch implements Comparable<ContentSummarySearch> {
    private final PdfFile file;
    private String summary = "";
    private boolean isSearched;

    public ContentSummarySearch(PdfFile file) {
        if (file == null) throw new IllegalArgumentException("Plik nie może być null.");
        this.file = file;
    }

    public ContentSummarySearch(PdfFile file, String summary) {
        this(file);
        setSummary(summary);
    }

    public PdfFile getFile() {
        return file;
    }

    public String getSummary() {
        return summary;
    }

    public boolean getIsSearched() {
        return isSearched;
    }

    public boolean getContainsSummary() {
        return !summary.isEmpty();
    }

    public void setSummary(String summary) {
        if (summary == null) throw new IllegalArgumentException("Streszczenie nie może być null.");
        this.summary = summary.trim();
        isSearched = true;
    }

    public void reset() {
        summary = null;
        isSearched = false;
    }

    @Override
    public int compareTo(ContentSummarySearch o) {
        return file.compareTo(o.getFile());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ContentSummarySearch other)) return false;
        return this.file.equals(other.file) && this.summary.equals(other.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, summary);
    }

    @Override
    public String toString() {
        return "ContentSummarySearch{" +
                "file=" + file +
                ", summary=" + summary +
                ", isSearched=" + isSearched +
                '}';
    }
}
