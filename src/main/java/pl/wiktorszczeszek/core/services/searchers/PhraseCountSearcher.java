package pl.wiktorszczeszek.core.services.searchers;

public interface PhraseCountSearcher extends PhraseSearchable {
    int search(String text);
}
