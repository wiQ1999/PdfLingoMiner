package pl.wiktorszczeszek.core.services.searchers;

public interface PhraseExistenceSearcher extends PhraseSearchable {
    boolean isExist(String text);
}
