package pl.wiktorszczeszek.core.services.fileLoctors;

import pl.wiktorszczeszek.core.domain.PdfFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class LocalPdfFilesLocator implements LocalFileLocator {
    @Override
    public Collection<PdfFile> locateFiles(String path) {
        File file = new File(path);
        ArrayList<PdfFile> files = new ArrayList<>();

        try {
            boolean isFile = file.isFile();
            boolean isDirectory = file.isDirectory();

            if (isFile) {
                boolean isPdf = isPdfFile(file);
                if (!isPdf) return files;
                PdfFile pdfFile = new PdfFile(path);
                files.add(pdfFile);
            } else if (isDirectory) {
                searchFilesInSubFolders(file, files);
            }
        } catch (Exception _) { }

        return files;
    }

    private void searchFilesInSubFolders(File folder, Collection<PdfFile> files) {
        for (File subFile : folder.listFiles()) {
            if (subFile.isDirectory()) {
                searchFilesInSubFolders(subFile, files);
            } else {
                boolean isPdf = isPdfFile(subFile);
                if (!isPdf) continue;
                String subFilePath = subFile.getPath();
                PdfFile pdfFile = new PdfFile(subFilePath);
                files.add(pdfFile);
            }
        }
    }

    @Override
    public boolean isExist(String path) {
        File file = new File(path);
        try {
            boolean isExist = file.exists();
            if (!isExist) return false;
            return isPdfFile(file);
        } catch (Exception _) {
            return false;
        }
    }

    private boolean isPdfFile(File file) {
        String fileName = file.getName();
        int lastIndex = fileName.lastIndexOf('.');

        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            String extension = fileName.substring(lastIndex + 1);
            return extension.equalsIgnoreCase("pdf");
        } else {
            return false;
        }
    }
}
