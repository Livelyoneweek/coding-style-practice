package com.callbus.mission.controller;

import com.callbus.mission.config.auth.PrincipalDetails;
import com.callbus.mission.entity.Like;
import com.callbus.mission.entity.Post;
import com.callbus.mission.service.LikeService;
import com.callbus.mission.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class LikeController {

    private final LikeService likeService;
    private final PostService postService;

    @PostMapping("/like/{id}")
    public ResponseEntity<?> saveAndDelete (@AuthenticationPrincipal PrincipalDetails userData, @PathVariable(value = "id") Long id) {
        log.info("### LikeController.save 실행");
        log.info("accountId={}", userData.getUsername());
        log.info("postId={}", id);

        Post post = postService.findById(id);
        Long likeCount = post.getLikeCount();

        if (likeService.findByPostIdAndMemberId(id, userData.getMember().getId()).isPresent()) {
            Like like = likeService.findByPostIdAndMemberId(id, userData.getMember().getId()).get();
            likeService.deleteById(like.getId());
            log.info("like 삭제 완료 likeId={}",like.getId());

            likeCount -=1;
        } else {
            Like like = new Like(userData.getMember(), post);
            likeService.save(like);
            log.info("like 생성 완료 likeId={}",like.getId());
            likeCount += 1;
        }
        postService.updateLikeCount(post, likeCount);
        log.info("post 좋아요 수 변경 완료 postId={}, likeCount={}",post.getId(),likeCount);

        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

}
