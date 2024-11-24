package pl.wiktorszczeszek.ui.controllers;

import pl.wiktorszczeszek.core.services.fileLoctors.LocalFileLocator;
import pl.wiktorszczeszek.core.services.fileReaders.TotalTextReader;
import pl.wiktorszczeszek.core.services.searchers.PhraseCountSearcher;
import pl.wiktorszczeszek.ui.views.MainFrame;

public class MainController {
    private MainFrame mainFrame;
    private SearchController searchController;
    private FileSelectionController fileSelectionController;

    public MainController(LocalFileLocator fileService, TotalTextReader readerService, PhraseCountSearcher searchService) {
        mainFrame = new MainFrame();

        searchController = new SearchController(
                mainFrame.getSearchPanel(),
                mainFrame.getResultsPanel(),
                readerService,
                searchService
        );

        fileSelectionController = new FileSelectionController(
                mainFrame.getFileSelectionPanel(),
                fileService
        );

        mainFrame.setVisible(true);
    }
}
