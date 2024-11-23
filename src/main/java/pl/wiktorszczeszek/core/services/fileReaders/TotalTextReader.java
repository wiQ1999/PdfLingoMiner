package pl.wiktorszczeszek.core.services.fileReaders;

import pl.wiktorszczeszek.core.domain.PdfFile;

public interface TotalTextReader {
    String allText(PdfFile file);
}
