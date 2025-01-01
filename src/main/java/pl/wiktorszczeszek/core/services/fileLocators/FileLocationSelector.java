package pl.wiktorszczeszek.core.services.fileLocators;

import pl.wiktorszczeszek.core.domain.PdfFile;

import java.util.Collection;

public interface FileLocationSelector {
    Collection<PdfFile> locateFiles(String path);
}
