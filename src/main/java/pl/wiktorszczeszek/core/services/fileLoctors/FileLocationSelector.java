package pl.wiktorszczeszek.core.services.fileLoctors;

import pl.wiktorszczeszek.core.domain.PdfFile;

import java.util.Collection;

public interface FileLocationSelector {
    Collection<PdfFile> locateFiles(String path);
}
