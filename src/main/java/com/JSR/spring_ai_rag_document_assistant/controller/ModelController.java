package com.JSR.spring_ai_rag_document_assistant.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelController {

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;


    @GetMapping("/api/model-info")
    public String getModel(){
        return "Current model is "+ model;
    }

}
