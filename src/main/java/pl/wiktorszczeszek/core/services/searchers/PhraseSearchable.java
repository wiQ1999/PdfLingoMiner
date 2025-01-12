package pl.wiktorszczeszek.core.services.searchers;

import pl.wiktorszczeszek.core.domain.SearchPhrase;

public interface PhraseSearchable {
    SearchPhrase getSearchPhrase();
    void setSearchPhrase(SearchPhrase value);
}
