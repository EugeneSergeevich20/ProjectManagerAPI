package com.manager.projectmanagerapi.controller;

import com.manager.projectmanagerapi.dto.CreateTagRequest;
import com.manager.projectmanagerapi.dto.TagDTO;
import com.manager.projectmanagerapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<TagDTO> createTag(@RequestBody CreateTagRequest request) {
        TagDTO tagDTO = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
