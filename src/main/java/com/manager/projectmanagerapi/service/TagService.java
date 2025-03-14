package com.manager.projectmanagerapi.service;

import com.manager.projectmanagerapi.dto.CreateTagRequest;
import com.manager.projectmanagerapi.dto.TagDTO;
import com.manager.projectmanagerapi.entity.Tag;
import com.manager.projectmanagerapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    /**
     * Все теги
     * @return
     */
    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Добавление нового тега
     * @param request
     * @return
     */
    public TagDTO createTag(CreateTagRequest request) {
        if (tagRepository.findByName(request.getName()).isPresent())
            throw new IllegalArgumentException("Tag already exists");

        Tag tag = tagRepository.save(Tag.builder().name(request.getName()).build());
        return new TagDTO(tag.getId(), tag.getName());
    }

}
