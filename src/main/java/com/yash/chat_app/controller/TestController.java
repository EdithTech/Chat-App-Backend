package com.yash.chat_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/testing")
    public ResponseEntity<?> Testing(){
        System.out.println("Working");
        return ResponseEntity.status(200).body("I am working....");
    }
}
