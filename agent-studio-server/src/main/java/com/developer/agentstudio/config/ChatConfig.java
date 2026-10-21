package com.developer.agentstudio.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatConfig {

    @Bean
    @Primary
    public ChatModel primaryChatModel(org.springframework.ai.google.genai.GoogleGenAiChatModel googleGenAiChatModel) {
        return googleGenAiChatModel;
    }
}