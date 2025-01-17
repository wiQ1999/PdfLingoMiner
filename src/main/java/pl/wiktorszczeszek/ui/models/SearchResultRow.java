package pl.wiktorszczeszek.ui.models;

public record SearchResultRow(
        String file,
        boolean isFileNameOccurrence,
        int textOccurrenceCount) { }
