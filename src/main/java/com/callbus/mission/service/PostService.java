package com.callbus.mission.service;

import com.callbus.mission.dto.PostDTO;
import com.callbus.mission.entity.Post;
import com.callbus.mission.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id가 없습니다"));
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public void update(Post post, PostDTO.Update updateDTO) {
        post.update(updateDTO);
    }
}
