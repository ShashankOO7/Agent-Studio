package com.developer.agentstudio.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiEmbeddingService implements EmbeddingModel {

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    private final WebClient webClient;

    public GeminiEmbeddingService(WebClient geminiWebClient) {
        this.webClient = geminiWebClient;
    }

    public List<Double> embedText(String text) {
        String model = "models/gemini-embedding-001";

        Map<String, Object> requestBody = Map.of(
                "content", Map.of("parts", List.of(Map.of("text", text))),
                "task_type", "RETRIEVAL_DOCUMENT"
        );

        Map<String, Object> response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + model + ":embedContent")
                        .queryParam("key", apiKey)
                        .build())
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> embedding = (Map<String, Object>) response.get("embedding");
        return (List<Double>) embedding.get("values");
    }

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        List<String> inputs = request.getInstructions();
        List<Embedding> embeddings = new java.util.ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            List<Double> values = embedText(inputs.get(i));
            float[] arr = new float[values.size()];
            for (int j = 0; j < values.size(); j++) arr[j] = values.get(j).floatValue();
            embeddings.add(new Embedding(arr, i));
        }
        return new EmbeddingResponse(embeddings);
    }

    @Override
    public float[] embed(Document document) {
        List<Double> values = embedText(document.getText());
        float[] arr = new float[values.size()];
        for (int i = 0; i < values.size(); i++) arr[i] = values.get(i).floatValue();
        return arr;
    }
}
