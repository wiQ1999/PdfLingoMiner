package pl.wiktorszczeszek.core.services.searchers;

import pl.wiktorszczeszek.core.domain.SearchPhrase;

import java.util.regex.Pattern;

public class TextSearcher implements PhraseExistenceSearcher, PhraseCountSearcher {
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
    public boolean isExist(String text) {
        return Pattern.compile(phrase.getPhrase(), Pattern.CASE_INSENSITIVE)
                .matcher(text)
                .find();
    }

    @Override
    public int search(String text) {
        return (int) Pattern.compile(phrase.getPhrase(), Pattern.CASE_INSENSITIVE)
                .splitAsStream(text)
                .count() - 1;
    }
}
