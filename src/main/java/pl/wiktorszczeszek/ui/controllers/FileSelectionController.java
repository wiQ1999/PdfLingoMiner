package pl.wiktorszczeszek.ui.controllers;

import pl.wiktorszczeszek.core.domain.PdfFile;
import pl.wiktorszczeszek.core.services.fileLoctors.LocalFileLocator;
import pl.wiktorszczeszek.ui.views.FileSelectionPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileSelectionController {
    private FileSelectionPanel fileSelectionPanel;
    private LocalFileLocator fileService;
    private List<PdfFile> selectedFiles;

    public FileSelectionController(FileSelectionPanel fileSelectionPanel, LocalFileLocator fileService) {
        this.fileSelectionPanel = fileSelectionPanel;
        this.fileService = fileService;

        initController();
    }

    private void initController() {
        fileSelectionPanel.addBrowseButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFiles();
            }
        });
    }

    private void selectFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(fileSelectionPanel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            selectedFiles = Arrays.stream(files)
                    .map(file -> new PdfFile(file.getAbsolutePath()))
                    .collect(Collectors.toList());

            // Aktualizacja w SearchController
            // Musisz mieć referencję do SearchController lub użyć wzorca obserwatora
            // searchController.setFilesToSearch(selectedFiles);
        }
    }

    public List<PdfFile> getSelectedFiles() {
        return selectedFiles;
    }
}
