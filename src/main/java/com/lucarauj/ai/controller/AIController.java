package com.lucarauj.ai.controller;

import com.lucarauj.ai.dto.MessageDTO;
import com.lucarauj.ai.factory.AIFactory;
import com.lucarauj.ai.factory.ContentRetrieverFactory;
import com.lucarauj.ai.factory.DocumentFactory;
import com.lucarauj.ai.factory.EmbeddingFactory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class AIController {
    @Value("${langchain.huggingface.accessToken}")
    private String token;

    @PostMapping
    public ResponseEntity chat(@RequestBody MessageDTO messageDTO){
        ChatLanguageModel chatModel = AIFactory.createHuggingFace(token);
        String response = chatModel.generate(messageDTO.message());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/rag")
    public ResponseEntity chatRag(@RequestBody MessageDTO messageDTO){
        ChatLanguageModel chatModel = AIFactory.createLocalChatModel();
        var embeddingModel = EmbeddingFactory.createEmbeddingModel();
        var embeddingStore = EmbeddingFactory.createEmbeddingStore();
        var fileContentRetriever = ContentRetrieverFactory.createFileContentRetriever(
                embeddingModel,
                embeddingStore,
                "content/movies.txt");

        var documentAssistant = new DocumentFactory(chatModel, fileContentRetriever);
        String response = documentAssistant.chat(messageDTO.message());
        return ResponseEntity.ok().body(response);
    }


}
