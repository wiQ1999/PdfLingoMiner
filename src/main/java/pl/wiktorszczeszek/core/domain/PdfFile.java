package pl.wiktorszczeszek.core.domain;

public record PdfFile(String path) implements Comparable<PdfFile> {
    public PdfFile(String path) {
        if (path == null) throw new IllegalArgumentException("Ścieżka do pliku nie może być null.");
        this.path = path.trim();
        if (this.path.isEmpty()) throw new IllegalArgumentException("Ścieżka do pliku nie może być pusta.");
    }

    @Override
    public int compareTo(PdfFile o) {
        return path.compareToIgnoreCase(o.path);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PdfFile other)) return false;
        return this.path.equalsIgnoreCase(other.path);
    }

    @Override
    public int hashCode() {
        return path.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "PdfFile{" +
                "path='" + path +
                '}';
    }
}
