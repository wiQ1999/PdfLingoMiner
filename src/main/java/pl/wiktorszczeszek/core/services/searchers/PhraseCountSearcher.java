package pl.wiktorszczeszek.core.services.searchers;

import pl.wiktorszczeszek.core.domain.SearchPhrase;

public interface PhraseCountSearcher {
    SearchPhrase getSearchPhrase();
    void setSearchPhrase(SearchPhrase value);
    int search(String text);
}
