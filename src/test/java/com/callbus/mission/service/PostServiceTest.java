package com.callbus.mission.service;

import com.callbus.mission.dto.AccountType;
import com.callbus.mission.dto.PostDTO;
import com.callbus.mission.dto.Quit;
import com.callbus.mission.entity.Member;
import com.callbus.mission.entity.Post;
import com.callbus.mission.repository.MemberRepository;
import com.callbus.mission.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    private Member member;

    @BeforeEach
    void init() {
        String password = bCryptPasswordEncoder.encode("임대인1234");
        member = new Member("임대인", AccountType.LESSOR, "임대인ID", password, Quit.REGISTER);
        memberRepository.save(member);
    }

    @Test
    @DisplayName("Post save 테스트")
    void save() {
        //given
        PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
        Post post = new Post(postDto, member);

        //when
        postService.save(post);

        //then
        Post findPost = postRepository.findById(post.getId()).get();
        assertEquals(post, findPost);
    }

    @Test
    @DisplayName("Post findById 테스트")
    void findById() {
        //given
        PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
        Post post = new Post(postDto, member);
        postRepository.save(post);

        //when
        Post findPost = postService.findById(post.getId());

        //then
        assertEquals(post, findPost);
    }

    @Test
    @DisplayName("Post findAll 테스트")
    void findAll() {
        //given
        PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
        Post post = new Post(postDto, member);
        postRepository.save(post);

        PostDTO.Request.Save postDto2 = new PostDTO.Request.Save("title2", "content2");
        Post post2 = new Post(postDto2, member);
        postRepository.save(post2);

        int page = 0;
        int size = 10;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page,size,sort);

        //when
        Page<Post> postPage = postService.findAll(pageable);

        //then
        assertEquals(10, postPage.getSize());
        assertEquals("title2", postPage.getContent().get(0).getTitle());
        assertEquals("title", postPage.getContent().get(1).getTitle());
    }


    @Test
    @DisplayName("Post update 테스트")
    void update() {
        //given
        PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
        Post post = new Post(postDto, member);
        postRepository.save(post);

        //when
        PostDTO.Request.Update updateDto = new PostDTO.Request.Update(post.getId(),"title2", "content2");
        postService.update(post,updateDto);

        //then
        Post findPost = postService.findById(post.getId());
        assertEquals("title2", findPost.getTitle());
        assertEquals("content2", findPost.getContent());
    }

    @Test
    @DisplayName("Post deleteById 테스트")
    void deleteById() {
        //given
        PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
        Post post = new Post(postDto, member);
        postRepository.save(post);

        //when
        postService.deleteById(post.getId());

        //then
        Optional<Post> findPost = postRepository.findById(post.getId());
        assertThrows(NoSuchElementException.class, () -> {
            findPost.get();
        });
    }

    @Test
    @DisplayName("Post updateLikeCount 테스트")
    void updateLikeCount() {
        //given
        PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
        Post post = new Post(postDto, member);
        postRepository.save(post);

        //when
        postService.updateLikeCount(post,1L);

        //then
        Post findPost = postRepository.findById(post.getId()).get();
        assertEquals(1L, findPost.getLikeCount());
    }
}