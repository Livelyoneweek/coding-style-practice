package com.callbus.mission.service;

import com.callbus.mission.dto.AccountType;
import com.callbus.mission.dto.PostDTO;
import com.callbus.mission.dto.Quit;
import com.callbus.mission.entity.Like;
import com.callbus.mission.entity.Member;
import com.callbus.mission.entity.Post;
import com.callbus.mission.repository.LikeRepository;
import com.callbus.mission.repository.MemberRepository;
import com.callbus.mission.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class LikeServiceTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeService likeService;

    @Autowired
    LikeRepository likeRepository;

    private Member member;
    private Post post;

    @BeforeEach
    void init() {
        String password = bCryptPasswordEncoder.encode("임대인1234");
        member = new Member("임대인", AccountType.LESSOR, "임대인ID", password, Quit.REGISTER);
        memberRepository.save(member);

        PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
        post = new Post(postDto, member);
        postRepository.save(post);
    }


    @Test
    void save() {
        //given
        Like like = new Like(member, post);

        //when
        likeService.save(like);

        //then
        Like findLike = likeRepository.findById(like.getId()).get();
        assertEquals(like, findLike);
    }

    @Test
    void deleteById() {
        //given
        Like like = new Like(member, post);
        likeRepository.save(like);

        //when
        likeService.deleteById(like.getId());


        //then
        Optional<Like> fineLike = likeRepository.findById(like.getId());
        assertThrows(NoSuchElementException.class, () -> {
            fineLike.get();
        });
    }

    @Test
    @DisplayName("Like find 테스트")
    void findByPostIdAndMemberId() {
        //given
        Like like = new Like(member, post);
        likeRepository.save(like);

        //when
        Optional<Like> findLike = likeService.findByPostIdAndMemberId(post.getId(), member.getId());

        //then
        assertEquals(like, findLike.get());
    }
}