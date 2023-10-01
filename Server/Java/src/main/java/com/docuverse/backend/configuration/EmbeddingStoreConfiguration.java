package com.docuverse.backend.configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingStoreConfiguration {

    Dotenv dotenv = Dotenv.load();

//    @Bean
//    public EmbeddingStore<TextSegment> embeddingStore() {
//        return new InMemoryEmbeddingStore<>();
//    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        EmbeddingStore<TextSegment> embeddingStore = mypinecone.builder()
                .apiKey(dotenv.get("PINECONE_API_KEY"))
                .environment(dotenv.get("PINECONE_ENVIRONMENT"))
                // Project ID can be found in the Pinecone url:
                // https://app.pinecone.io/organizations/{organization}/projects/{environment}:{projectId}/indexes
                .projectId(dotenv.get("PINECONE_PROJECTID"))
                // Make sure the dimensions of the Pinecone index match the dimensions of the embedding model
                // (384 for all-MiniLM-L6-v2, 1536 for text-embedding-ada-002, etc.)
                .index(dotenv.get("PINECONE_INDEX"))
                .build();

        return embeddingStore;
    }


}

