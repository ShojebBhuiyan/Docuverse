package com.docuverse.backend.services;

import com.docuverse.backend.dtos.ChatRequestDTO;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import io.github.cdimascio.dotenv.Dotenv;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;
import static java.time.Duration.ofSeconds;
import static org.mapdb.Serializer.STRING;

@Service
public class ChatServiceImpl implements ChatService {

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public ChatServiceImpl(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }


    @Override
    public String processChat(ChatRequestDTO request) {
        Dotenv dotenv = Dotenv.load();

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

        try {
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

            String answer = chain.execute(request.question());
            System.out.println(chatMemory.messages());
            return answer;
        } catch (Exception e) {
            // Handle the exception here, you can log it or return an error message
            e.printStackTrace(); // Print the exception stack trace for debugging
            return "An error occurred while processing the request.";
        }
    }

//    public String processChat(ChatRequest request) {
//
//        Dotenv dotenv = Dotenv.load();
//
//        // Create a prompt template
//        PromptTemplate promptTemplate = PromptTemplate.from(
//                "Answer the question as truthfully as possible using the information below, and if the answer is not within the information, say 'I don't know.\n"
//                        + "\n"
//                        + "Question:\n"
//                        + "{{question}}\n"
//                        + "\n"
//                        + "Information:\n"
//                        + "{{information}}");
//
//        System.out.println("checkpoint 3");
//
//
//        // Send the prompt to the OpenAI chat model
//        ChatLanguageModel chatModel = OpenAiChatModel.builder()
//                .apiKey(dotenv.get("OPENAI_API_KEY"))
//                .modelName(GPT_3_5_TURBO)
//                .temperature(0.7)
//                .timeout(ofSeconds(15))
//                .maxRetries(3)
//                .logResponses(true)
//                .logRequests(true)
//                .build();
//
//        ChatMemory chatMemory = MessageWindowChatMemory.builder()
//                .maxMessages(10)
//                .chatMemoryStore(new PersistentChatMemoryStore())
//                .build();
//
//        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
//                .chatLanguageModel(chatModel)
//                .retriever(EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, 1, 0.9))
//                .chatMemory(chatMemory) // you can override default chat memory
//                .promptTemplate(promptTemplate) // you can override default prompt template
//                .build();
//
//        String answer = chain.execute(request.getQuestion());
//        System.out.println(chatMemory.messages());
//        return answer;
//    }

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

