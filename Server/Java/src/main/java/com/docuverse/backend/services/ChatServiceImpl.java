package com.docuverse.backend.services;

import com.docuverse.backend.models.ChatRequest;
import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.FileSystemDocumentLoader;
import dev.langchain4j.data.document.UrlDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import io.github.cdimascio.dotenv.Dotenv;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;
import static dev.langchain4j.model.openai.OpenAiModelName.TEXT_EMBEDDING_ADA_002;
import static java.time.Duration.ofSeconds;
import static org.mapdb.Serializer.STRING;

@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public String processChat(ChatRequest request,EmbeddingStore<TextSegment> embeddingStore) {

        Dotenv dotenv = Dotenv.load();

        EmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(dotenv.get("OPENAI_API_KEY"))
                .modelName(TEXT_EMBEDDING_ADA_002)
                .timeout(ofSeconds(15))
                .logRequests(false)
                .logResponses(false)
                .build();

        //EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // Create a prompt template
        PromptTemplate promptTemplate = PromptTemplate.from(
                "Answer the question as truthfully as possible using the information below, and if the answer is not within the information, say 'I don't know.\n"
                        + "\n"
                        + "Question:\n"
                        + "{{question}}\n"
                        + "\n"
                        + "Information:\n"
                        + "{{information}}");

        System.out.println("checkpoint 3");


        // Send the prompt to the OpenAI chat model
        ChatLanguageModel chatModel = OpenAiChatModel.builder()
                .apiKey(dotenv.get("OPENAI_API_KEY"))
                .modelName(GPT_3_5_TURBO)
                .temperature(0.7)
                .timeout(ofSeconds(15))
                .maxRetries(3)
                .logResponses(true)
                .logRequests(true)
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryStore(new PersistentChatMemoryStore())
                .build();

        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
                .chatLanguageModel(chatModel)
                .retriever(EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, 1, 0.9))
                .chatMemory(chatMemory) // you can override default chat memory
                .promptTemplate(promptTemplate) // you can override default prompt template
                .build();

        String answer = chain.execute(request.getQuestion());
        System.out.println(chatMemory.messages());
        return answer;
    }

    static class PersistentChatMemoryStore implements ChatMemoryStore {

        private final DB db = DBMaker.fileDB("chat-memory.db").transactionEnable().make();
        private final Map<String, String> map = db.hashMap("messages", STRING, STRING).createOrOpen();

        @Override
        public List<ChatMessage> getMessages(Object memoryId) {
            String json = map.get((String) memoryId);
            return messagesFromJson(json);
        }

        @Override
        public void updateMessages(Object memoryId, List<ChatMessage> messages) {
            String json = messagesToJson(messages);
            map.put((String) memoryId, json);
            db.commit();
        }

        @Override
        public void deleteMessages(Object memoryId) {
            map.remove((String) memoryId);
            db.commit();
        }
    }
}

