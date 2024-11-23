package pl.wiktorszczeszek.core.services.fileReaders;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import pl.wiktorszczeszek.core.domain.PdfFile;

import java.io.File;
import java.io.IOException;

public class PdfBoxReader implements TotalTextReader {
    @Override
    public String allText(PdfFile file) {
        String text = "";
        try (PDDocument document = Loader.loadPDF(new File(file.getPath()))) {
            if (document == null || document.isEncrypted()) {
                return text;
            }

            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
        }
        catch (IOException ex) {
            return text;
        }

        return text;
    }
}
