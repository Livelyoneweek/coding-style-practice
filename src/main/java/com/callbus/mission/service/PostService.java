package com.callbus.mission.service;

import com.callbus.mission.dto.PostDTO;
import com.callbus.mission.entity.Post;
import com.callbus.mission.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id가 없습니다"));
    }

    public void update(Post post, PostDTO.Request.Update updateDTO) {
        post.update(updateDTO);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostDTO.Response.PostPage> changeDtos(Page<Post> postList) {
        return postList.stream()
                .map(m -> PostDTO.Response.PostPage.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .likeCount(m.getLikeCount())
                        .likeCheck(false)
                        .createDate(m.getCreateDate())
                        .modifiedDate(m.getModifiedDate())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<PostDTO.Response.PostPage> changeDtos(Page<Post> postList,Long memberId) {
//        List<PostDTO.Response.PostPage> postPageList = postList.stream().map(PostDTO.Response.PostPage::new).collect(Collectors.toList());
        return postList.stream()
                .map(m -> PostDTO.Response.PostPage.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .likeCount(m.getLikeCount())
                        .likeCheck(m.getLikeList().stream().anyMatch(like -> like.getId().equals(memberId)))
                        .createDate(m.getCreateDate())
                        .modifiedDate(m.getModifiedDate())
                        .build()
                ).collect(Collectors.toList());
    }

    public void updateLikeCount(Post post, Long likeCount) {
        post.updateLikeCount(likeCount);
    }
}
