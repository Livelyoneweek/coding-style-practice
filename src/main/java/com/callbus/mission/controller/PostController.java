package com.callbus.mission.controller;

import com.callbus.mission.config.auth.PrincipalDetails;
import com.callbus.mission.dto.PostDTO;
import com.callbus.mission.entity.Post;
import com.callbus.mission.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal PrincipalDetails userData,
                                     @PageableDefault(page=0, size=10, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
        log.info("### PostController.findAll 실행");

        Page<Post> postList = postService.findAll(pageable);
        List<PostDTO.Response.PostPage> postPages;
        if (userData == null) {
            postPages = postService.changeDtos(postList);
        }else {
            postPages = postService.changeDtos(postList, userData.getMember().getId());
        }
        return new ResponseEntity<>(postPages, HttpStatus.OK);
    }

    @PostMapping("/auth/post")
    public ResponseEntity<?> save(PostDTO.Request.Save postDTO, @AuthenticationPrincipal PrincipalDetails userData) {
        log.info("### PostController.save 실행");
        log.info("accountId={}", userData.getUsername());
        log.info("postDTO={}", postDTO.toString());

        Post post = new Post(postDTO, userData.getMember());
        postService.save(post);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PutMapping("/auth/post")
    public ResponseEntity<?> update(PostDTO.Request.Update postDTO, @AuthenticationPrincipal PrincipalDetails userData) {
        log.info("### PostController.update 실행");
        log.info("accountId={}", userData.getUsername());
        log.info("postDTO={}", postDTO.toString());

        Post post = postService.findById(postDTO.getId());
        postService.update(post, postDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @DeleteMapping("/auth/post/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id")Long id, @AuthenticationPrincipal PrincipalDetails userData) {
        log.info("### PostController.delete 실행");
        log.info("accountId={}", userData.getUsername());

        Post findPost = postService.findById(id);
        if (findPost.getMember().getId().equals(userData.getMember().getId())) {
            postService.deleteById(id);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return new ResponseEntity<>("FAIL", HttpStatus.OK);
    }

}


