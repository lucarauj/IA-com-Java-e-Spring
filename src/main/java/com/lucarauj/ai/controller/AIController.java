package com.lucarauj.ai.controller;

import com.lucarauj.ai.dto.MessageDTO;
import com.lucarauj.ai.factory.AIFactory;
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
}
