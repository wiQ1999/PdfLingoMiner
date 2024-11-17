package pl.wiktorszczeszek.core.domain;

public class SearchPhrase {
    private final String phrase;

    public SearchPhrase() {
        phrase = "";
    }

    public SearchPhrase(String phrase) {
        if (phrase == null) throw new IllegalArgumentException("Fraza wyszukiwania nie może być null.");
        this.phrase = phrase.trim();
    }

    public String getPhrase() {
        return phrase;
    }

    public boolean isValid() {
        return phrase.length() > 1 && phrase.length() < 101;
    }

    @Override
    public String toString() {
        return "SearchPhrase{" +
                "phrase='" + phrase +
                '}';
    }
}
