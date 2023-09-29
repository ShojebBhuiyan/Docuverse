package com.docuverse.backend.services;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;
import static dev.langchain4j.model.openai.OpenAiModelName.TEXT_EMBEDDING_ADA_002;
import static java.time.Duration.ofSeconds;

@Service
public class DocumentService {

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public DocumentService(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }


    public String extractTextFromPDF(MultipartFile pdfFile) throws IOException {
        System.out.println("Inside service");
        try (InputStream is = pdfFile.getInputStream();
                PDDocument document = Loader.loadPDF(is.readAllBytes());) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            //System.out.println(text);
            document.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Implement the method to create Metadata from the MultipartFile
    public Metadata createMetadataFromMultipartFile(MultipartFile file) {
        Metadata metadata = new Metadata();

        try {
            // Extract relevant metadata from the MultipartFile
            metadata.add("FileName", file.getOriginalFilename());
            metadata.add("ContentType", file.getContentType());
            // You can add more metadata fields as needed

        } catch (Exception e) {
            // Handle exceptions here (e.g., log them)
            e.printStackTrace();
            // You can also add a default value or handle the error in another way
        }

        return metadata;
    }

//    public void documentToEmbedding(Document document){
//        Dotenv dotenv = Dotenv.load();
//
//        //Splitting document to multiple segments with segment size
//        DocumentSplitter splitter = DocumentSplitters.recursive(100, new OpenAiTokenizer(GPT_3_5_TURBO));
//        List<TextSegment> docSegments = splitter.split(document);
//
//        System.out.println("checkpoint 1");
//
//        //Create embeddings
//        List<Embedding> embeddings = embeddingModel.embedAll(docSegments);
//        System.out.println("checkpoint 2");
//
//        // Store embeddings into an embedding store for further search / retrieval
//
//        //TODO pine cone implementation here
////        EmbeddingStore<TextSegment> embeddingStore = mypinecone.builder()
////                .apiKey(dotenv.get("PINECONE_API_KEY"))
////                .environment(dotenv.get("PINECONE_ENVIRONMENT"))
////                // Project ID can be found in the Pinecone url:
////                // https://app.pinecone.io/organizations/{organization}/projects/{environment}:{projectId}/indexes
////                .projectId(dotenv.get("PINECONE_PROJECTID"))
////                // Make sure the dimensions of the Pinecone index match the dimensions of the embedding model
////                // (384 for all-MiniLM-L6-v2, 1536 for text-embedding-ada-002, etc.)
////                .index(dotenv.get("PINECONE_INDEX"))
////                .build();
//
//        System.out.println("checkpoint 3");
//
//        //storing embeddings
//        embeddingStore.addAll(embeddings, docSegments);
//
//        System.out.println("all good");
//    }

    public void documentToEmbedding(Document document) {
        Dotenv dotenv = Dotenv.load();

        // Splitting document to multiple segments with segment size
        DocumentSplitter splitter = DocumentSplitters.recursive(100, new OpenAiTokenizer(GPT_3_5_TURBO));
        List<TextSegment> docSegments = splitter.split(document);

        System.out.println("checkpoint 1");

        // Create embeddings
        List<Embedding> embeddings = null;
        try {
            embeddings = embeddingModel.embedAll(docSegments);
            System.out.println("checkpoint 2");

            // Store embeddings into an embedding store for further search / retrieval

            // TODO pine cone implementation here
            // EmbeddingStore<TextSegment> embeddingStore = mypinecone.builder()
            //     .apiKey(dotenv.get("PINECONE_API_KEY"))
            //     .environment(dotenv.get("PINECONE_ENVIRONMENT"))
            //     // Project ID can be found in the Pinecone url:
            //     // https://app.pinecone.io/organizations/{organization}/projects/{environment}:{projectId}/indexes
            //     .projectId(dotenv.get("PINECONE_PROJECTID"))
            //     // Make sure the dimensions of the Pinecone index match the dimensions of the embedding model
            //     // (384 for all-MiniLM-L6-v2, 1536 for text-embedding-ada-002, etc.)
            //     .index(dotenv.get("PINECONE_INDEX"))
            //     .build();

            System.out.println("checkpoint 3");

            // Storing embeddings
            if (embeddingStore != null) {
                embeddingStore.addAll(embeddings, docSegments);
                System.out.println("all good");
            } else {
                System.err.println("Embedding store is not initialized.");
            }
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace();
            System.err.println("An error occurred during embedding generation or storage.");
        }
    }


}
