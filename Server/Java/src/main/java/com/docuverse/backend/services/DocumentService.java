package com.docuverse.backend.services;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class DocumentService {
    public String extractTextFromPDF(MultipartFile pdfFile) throws IOException {
        try (InputStream is = pdfFile.getInputStream();
             PDDocument document = Loader.loadPDF(is.readAllBytes());) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            System.out.println(text);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
