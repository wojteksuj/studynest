package org.example.studynest.controller;

import org.example.studynest.dto.request.CreateTopicDTO;
import org.example.studynest.dto.response.TopicDTO;
import org.example.studynest.security.CustomUserDetails;
import org.example.studynest.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public List<TopicDTO> getTopicsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return topicService.getAllTopicsByUser(userDetails.getId());
    }

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@AuthenticationPrincipal CustomUserDetails userDetails ,
                                                @RequestBody CreateTopicDTO createTopicDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(topicService.createTopic(userDetails.getId(), createTopicDTO));
    }
}
