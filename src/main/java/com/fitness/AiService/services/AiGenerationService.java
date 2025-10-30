package com.fitness.AiService.services;

import com.fitness.AiService.dtos.AiGeneratedRecommendationDTO;
import com.fitness.AiService.models.Activity;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiGenerationService {

    // Inject the underlying ChatModel bean provided by the starter
    private final ChatModel chatModel;

    public AiGeneratedRecommendationDTO generateRecommendation(Activity activity) {

        // Manually create the ChatClient instance using the builder
        ChatClient chatClient = ChatClient.builder(chatModel).build();

        // This converter tells Spring AI to force the LLM's output
        // into the structure of our AiGeneratedRecommendation class.
        // Ensure AiGeneratedRecommendation class exists and matches the expected structure
        BeanOutputConverter<AiGeneratedRecommendationDTO> outputConverter =
                new BeanOutputConverter<>(AiGeneratedRecommendationDTO.class);

        PromptTemplate promptTemplate = getPromptTemplate();
        Prompt prompt = promptTemplate.create(Map.of(
                "activity", activity.toString(), // Pass the full activity object
                "format", outputConverter.getFormat() // Injects the JSON schema
        ));

        // Now use the manually created chatClient instance
        // Ensure AiGeneratedRecommendation class is the correct one to use here
        return chatClient.prompt(prompt)
                .call()
                .entity(AiGeneratedRecommendationDTO.class);
    }

    @NotNull
    private static PromptTemplate getPromptTemplate() {
        String systemMessage = """
                You are an expert personal trainer and fitness analyst.
                A user has just logged a new activity. Your task is to provide a detailed,
                insightful, and encouraging analysis of their performance.

                Analyze the provided activity data: {activity}
                
                Based on this activity, generate a comprehensive recommendation.
                Your response MUST strictly be in the following JSON format:
                {format}
                """;

        return new PromptTemplate(systemMessage);
    }
}