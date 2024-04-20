package com.lucarauj.ai.factory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;

import java.time.Duration;

public class AIFactory {

    public static ChatLanguageModel createHuggingFace(String accessToken) {
        return HuggingFaceChatModel.builder()
                .accessToken(accessToken)
                .modelId("EleutherAI/gpt-neo-125m")
                .timeout(Duration.ofSeconds(300))
                .build();
    }
}
