package pl.wiktorszczeszek.ui;

import pl.wiktorszczeszek.core.services.fileLocators.LocalFileLocator;
import pl.wiktorszczeszek.core.services.fileLocators.LocalPdfFilesLocator;
import pl.wiktorszczeszek.core.services.fileReaders.PdfBoxReader;
import pl.wiktorszczeszek.core.services.fileReaders.TotalTextReader;
import pl.wiktorszczeszek.core.services.searchers.PhraseCountSearcher;
import pl.wiktorszczeszek.core.services.searchers.TextSearcher;
import pl.wiktorszczeszek.ui.controllers.MainController;

public class Main {
    public static void main(String[] args) {
        LocalFileLocator fileService = new LocalPdfFilesLocator();
        TotalTextReader readerService = new PdfBoxReader();
        PhraseCountSearcher searchService = new TextSearcher();
        new MainController(fileService, readerService, searchService);
    }
}