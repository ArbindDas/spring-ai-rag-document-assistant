package com.JSR.spring_ai_rag_document_assistant.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/chat")
public class ChatController {


    private final ChatClient client;


    public ChatController(ChatClient.Builder builder){
        this.client = builder.build();
    }

    @GetMapping("/working")
    public ResponseEntity<String> getWorking(@RequestParam String Q){

      var resultResponse =   client.prompt(Q).call().content();
        return ResponseEntity.ok(resultResponse);
    }

}
