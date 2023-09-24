package com.docuverse.backend.configuration;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.UrlDocumentLoader;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.net.URL;

import static dev.langchain4j.model.openai.OpenAiModelName.TEXT_EMBEDDING_ADA_002;
import static dev.langchain4j.data.document.UrlDocumentLoader.load;

public class PineconeConfiguration {

    private static Path toPath(String fileName) {
        try {
            URL fileUrl = PineconeConfiguration.class.getResource(fileName);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    Dotenv dotenv = Dotenv.load();

    @Bean
    EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) {
        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore
                .builder()
                .apiKey(dotenv.get("PINECONE_API_KEY"))
                .environment(dotenv.get("PINECONE_ENVIRONMENT"))
                .projectId(dotenv.get("PINECONE_PROJECTID"))
                .index(dotenv.get("PINECONE_INDEX"))
                .build();

//        URL fileUrl = "some";
//        Document document = load()
        return embeddingStore;
    }


    OpenAiEmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
            .apiKey(dotenv.get("OPENAI_API_KEY"))
            .modelName(TEXT_EMBEDDING_ADA_002)
            .build();

    String url = "";




}
