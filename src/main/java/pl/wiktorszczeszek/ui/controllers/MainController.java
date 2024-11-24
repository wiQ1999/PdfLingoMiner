package pl.wiktorszczeszek.ui.controllers;

import pl.wiktorszczeszek.core.domain.PdfFile;
import pl.wiktorszczeszek.core.domain.SearchContext;
import pl.wiktorszczeszek.core.domain.SearchPhrase;
import pl.wiktorszczeszek.core.domain.SearchResult;
import pl.wiktorszczeszek.core.services.fileLoctors.LocalFileLocator;
import pl.wiktorszczeszek.core.services.fileReaders.TotalTextReader;
import pl.wiktorszczeszek.core.services.searchers.PhraseCountSearcher;
import pl.wiktorszczeszek.ui.views.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    private LocalFileLocator fileService;
    private TotalTextReader readerService;
    private PhraseCountSearcher searchService;
    private SearchContext searchContext;
    private MainFrame mainFrame;

    public MainController(LocalFileLocator fileService, TotalTextReader readerService, PhraseCountSearcher searchService) {
        this.fileService = fileService;
        this.readerService = readerService;
        this.searchService = searchService;
        searchContext = new SearchContext();
        mainFrame = new MainFrame();
        mainFrame.addBrowseButtonListener(e -> selectLocalFiles());
        mainFrame.addSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }

    private void selectLocalFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            List<String> paths = Arrays.stream(files)
                    .map(File::getAbsolutePath)
                    .collect(Collectors.toList());
            for (String path : paths) {
                PdfFile[] pdfFiles = fileService
                        .locateFiles(path)
                        .toArray(new PdfFile[0]);
                if (pdfFiles.length > 0) {
                    searchContext.addFiles(pdfFiles);
                }
            }

            paths = searchContext.getFiles().stream()
                    .map(PdfFile::getPath)
                    .collect(Collectors.toList());

            Collections.sort(paths);
            mainFrame.setFiles(paths);
        }
    }

    private void performSearch() {
        String phraseText = mainFrame.getPhraseFieldText();

        SearchPhrase phrase = new SearchPhrase(phraseText);
        if (!phrase.isValid()){
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Nieprawidłowa fraza wyszukiwania.",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Set<PdfFile> filesToSearch = new HashSet<>(searchContext.getFiles());
        if (filesToSearch.isEmpty()) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Nie wybrano plików do przeszukania.",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    searchService.setSearchPhrase(phrase);
                    for (PdfFile file : filesToSearch) {
                        String text = readerService.allText(file);
                        int occurrence = searchService.search(text);
                        SearchResult result = new SearchResult(file, phrase, occurrence);
                        searchContext.updateSearchResult(result);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    List<SearchResult> results = searchContext.getResults()
                            .stream()
                            .filter(result -> result.getOccurrenceCount() > 0)
                            .sorted()
                            .collect(Collectors.toList());
                    mainFrame.setResults(results);
                }
            };
            worker.execute();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    ex.getMessage(),
                    "Wystąpił błąd podczas wyszukiwania",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
