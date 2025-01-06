package pl.wiktorszczeszek.ui.controllers;

import pl.wiktorszczeszek.core.domain.PdfFile;
import pl.wiktorszczeszek.core.domain.SearchContext;
import pl.wiktorszczeszek.core.domain.SearchPhrase;
import pl.wiktorszczeszek.core.domain.results.TextContentSearch;
import pl.wiktorszczeszek.core.services.fileLocators.LocalFileLocator;
import pl.wiktorszczeszek.core.services.fileReaders.TotalTextReader;
import pl.wiktorszczeszek.core.services.searchers.PhraseCountSearcher;
import pl.wiktorszczeszek.ui.views.MainFrame;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    private LocalFileLocator fileService;
    private TotalTextReader readerService;
    private PhraseCountSearcher searchService;
    private SearchContext searchContext;
    private MainFrame mainFrame;
    private SwingWorker<Void, Integer> searchWorker;

    public MainController(LocalFileLocator fileService, TotalTextReader readerService, PhraseCountSearcher searchService) {
        this.fileService = fileService;
        this.readerService = readerService;
        this.searchService = searchService;
        searchContext = new SearchContext();
        mainFrame = new MainFrame();
        mainFrame.addBrowseButtonListener(_ -> selectLocalFiles());
        mainFrame.addRemoveButtonListener(_ -> removeFiles());
        mainFrame.addPerformSearchButtonListener(_ -> performSearch());
        mainFrame.addCancelSearchButtonListener(_ -> cancelSearch());
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

            paths = getPaths();

            mainFrame.setFiles(paths);
            mainFrame.setSearchCapacity(paths.size());
        }
    }

    private void removeFiles() {
        int[] indexes = mainFrame.getSelectedFileIndexes();
        ArrayList<PdfFile> toRemove = new ArrayList<>(indexes.length);
        for (int index : indexes) {
            ArrayList<PdfFile> files = new ArrayList<>(searchContext.getFiles());
            PdfFile file = files.get(index);
            toRemove.add(file);
        }

        if (toRemove.isEmpty()) return;

        PdfFile[] toRemoveArray = toRemove.toArray(new PdfFile[0]);
        searchContext.removeFiles(toRemoveArray);
        List<String> paths = getPaths();
        mainFrame.setFiles(paths);
        mainFrame.setSearchCapacity(paths.size());
    }

    private List<String> getPaths() {
        return searchContext.getFiles().stream()
                .map(PdfFile::path)
                .collect(Collectors.toList());
    }

    private void performSearch() {
        mainFrame.setSearchedCount(0);
        String phraseText = mainFrame.getPhraseFieldText();

        SearchPhrase phrase = new SearchPhrase(phraseText);
        if (!phrase.isValid()){
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Nieprawidłowa fraza wyszukiwania.",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
            mainFrame.setSearchButtonToSearch();
            return;
        }

        ArrayList<PdfFile> files = new ArrayList<>(searchContext.getFiles());
        if (files.isEmpty()) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Nie wybrano plików do przeszukania.",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
            mainFrame.setSearchButtonToSearch();
            return;
        }

        try {
            searchWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    searchService.setSearchPhrase(phrase);
                    for (int i = 0; i < files.size(); i++) {
                        if (isCancelled()) break;
                        PdfFile file = files.get(i);
                        String text = readerService.allText(file);
                        int occurrence = searchService.search(text);
                        TextContentSearch result = new TextContentSearch(file, phrase, occurrence);
                        searchContext.updateTextContentResult(result);
                        publish(i + 1);
                    }
                    return null;
                }

                @Override
                protected void process(List<Integer> chunks) {
                    int last = chunks.getLast();
                    mainFrame.setSearchedCount(last);
                }

                @Override
                protected void done() {
                    List<TextContentSearch> results = searchContext.getTextContentResults()
                            .stream()
                            .filter(result -> result.getOccurrenceCount() > 0)
                            .collect(Collectors.toList());
                    mainFrame.setResults(results);
                    mainFrame.setSearchButtonToSearch();
                }
            };
            searchWorker.execute();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    ex.getMessage(),
                    "Wystąpił błąd podczas wyszukiwania",
                    JOptionPane.ERROR_MESSAGE);
            mainFrame.setSearchButtonToSearch();
        }
    }

    private void cancelSearch() {
        if (searchWorker != null && !searchWorker.isDone()) {
            searchWorker.cancel(true);
        }
    }
}
