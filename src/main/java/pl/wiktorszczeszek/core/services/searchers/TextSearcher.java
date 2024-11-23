package pl.wiktorszczeszek.core.services.searchers;

import org.springframework.util.StringUtils;
import pl.wiktorszczeszek.core.domain.SearchPhrase;

public class TextSearcher implements PhraseCountSearcher {
    private SearchPhrase phrase;

    @Override
    public SearchPhrase getSearchPhrase() {
        return phrase;
    }

    @Override
    public void setSearchPhrase(SearchPhrase value) {
        if (value == null) throw new IllegalArgumentException("Fraza wyszukiwania nie może być null.");
        if (!value.isValid()) throw new IllegalArgumentException("Fraza wyszukiwania jest niepoprawna.");
        phrase = value;
    }

    @Override
    public int search(String text) {
        return StringUtils.countOccurrencesOf(text, phrase.getPhrase());
    }
}
