package pl.wiktorszczeszek.ui.controllers;

import pl.wiktorszczeszek.core.domain.PdfFile;
import pl.wiktorszczeszek.core.domain.SearchPhrase;
import pl.wiktorszczeszek.core.domain.SearchResult;
import pl.wiktorszczeszek.core.services.fileReaders.TotalTextReader;
import pl.wiktorszczeszek.core.services.searchers.PhraseCountSearcher;
import pl.wiktorszczeszek.ui.views.ResultsPanel;
import pl.wiktorszczeszek.ui.views.SearchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private SearchPanel searchPanel;
    private ResultsPanel resultsPanel;
    private TotalTextReader readerService;
    private PhraseCountSearcher searchService;
    private List<PdfFile> filesToSearch;

    public SearchController(SearchPanel searchPanel, ResultsPanel resultsPanel, TotalTextReader readerService, PhraseCountSearcher searchService) {
        this.searchPanel = searchPanel;
        this.resultsPanel = resultsPanel;
        this.readerService = readerService;
        this.searchService = searchService;

        initController();
    }

    private void initController() {
        searchPanel.addSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }

    public void setFilesToSearch(List<PdfFile> files) {
        this.filesToSearch = files;
    }

    private void performSearch() {
        String phraseText = searchPanel.getSearchPhrase();
        try {
            SearchPhrase phrase = new SearchPhrase(phraseText);

            if (filesToSearch == null || filesToSearch.isEmpty()) {
                JOptionPane.showMessageDialog(searchPanel, "Nie wybrano plików do przeszukania.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Wykonaj wyszukiwanie w osobnym wątku
            SwingWorker<List<SearchResult>, Void> worker = new SwingWorker<>() {
                @Override
                protected List<SearchResult> doInBackground() throws Exception {
                    List<SearchResult> results = new ArrayList<>();
                    searchService.setSearchPhrase(phrase);
                    for (PdfFile file : filesToSearch) {
                        String text = readerService.allText(file);
                        int occurrence = searchService.search(text);
                        results.add(new SearchResult(file, phrase, occurrence));
                    }
                    return results;
                }

                @Override
                protected void done() {
                    try {
                        List<SearchResult> results = get();
                        resultsPanel.updateResults(results);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(searchPanel, "Wystąpił błąd podczas wyszukiwania.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(searchPanel, ex.getMessage(), "Nieprawidłowa fraza", JOptionPane.ERROR_MESSAGE);
        }
    }
}
