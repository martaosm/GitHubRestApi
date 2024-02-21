package com.example.GitHubRestApi.controller;

import com.example.GitHubRestApi.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GitHubController {

    @Autowired
    GitHubService gitHubService;

    @GetMapping("/repos")
    public ResponseEntity<?> getUserRepositories(@RequestParam String username){
        return gitHubService.getUserRepositories(username);
    }
}
