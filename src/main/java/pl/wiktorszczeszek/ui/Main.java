package pl.wiktorszczeszek.ui;

import pl.wiktorszczeszek.core.services.fileLoctors.LocalFileLocator;
import pl.wiktorszczeszek.core.services.fileLoctors.LocalPdfFilesLocator;
import pl.wiktorszczeszek.core.services.fileReaders.PdfBoxReader;
import pl.wiktorszczeszek.core.services.fileReaders.TotalTextReader;
import pl.wiktorszczeszek.core.services.searchers.PhraseCountSearcher;
import pl.wiktorszczeszek.core.services.searchers.TextSearcher;
import pl.wiktorszczeszek.ui.controllers.MainController;

public class Main {
    public static void main(String[] args) {
        // Tworzenie instancji serwisów
        LocalFileLocator fileService = new LocalPdfFilesLocator();
        TotalTextReader readerService = new PdfBoxReader();
        PhraseCountSearcher searchService = new TextSearcher();

        // Inicjalizacja kontrolera głównego
        new MainController(fileService, readerService, searchService);
    }
}