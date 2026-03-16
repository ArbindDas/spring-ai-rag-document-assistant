package com.JSR.spring_ai_rag_document_assistant.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/user-info")
    public ResponseEntity<UserInfo> getUserName(){
         UserInfo userInfo = new UserInfo("Arbind das", "java developer");
         return ResponseEntity.ok(userInfo);
    }

   public record UserInfo(String name , String roll){}
}
