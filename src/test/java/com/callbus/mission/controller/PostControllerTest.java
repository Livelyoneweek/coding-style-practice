package com.callbus.mission.controller;

import com.callbus.mission.config.auth.PrincipalDetails;
import com.callbus.mission.dto.AccountType;
import com.callbus.mission.dto.PostDTO;
import com.callbus.mission.dto.Quit;
import com.callbus.mission.entity.Member;
import com.callbus.mission.entity.Post;
import com.callbus.mission.repository.MemberRepository;
import com.callbus.mission.repository.PostRepository;
import com.callbus.mission.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

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
    @DisplayName("Post findALl 테스트")
    void findAll() throws Exception {

        mockMvc.perform(get("/post"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Post save 테스트")
    void save() throws Exception {
        MockHttpSession session = getMockHttpSession();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "title2");
        params.add("content", "content2");
        mockMvc.perform(post("/auth/post")
                        .session(session)
                        .params(params)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Post update 테스트")
    void update() throws Exception {
        MockHttpSession session = getMockHttpSession();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", String.valueOf(post.getId()));
        params.add("title", "title3");
        params.add("content", "content3");
        mockMvc.perform(put("/auth/post")
                        .session(session)
                        .params(params)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Post delete 테스트")
    void delete() throws Exception {
        MockHttpSession session = getMockHttpSession();
        mockMvc.perform(MockMvcRequestBuilders.delete("/auth/post/{id}", post.getId())
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    //시큐리티 인증처리를 위한 세션 추가
    private MockHttpSession getMockHttpSession() {
        UserDetails userDetails = new PrincipalDetails(member);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, new SecurityContextImpl(new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return userDetails.getAuthorities();}
                    @Override
                    public Object getCredentials() {
                        return userDetails.getPassword();
                    }
                    @Override
                    public Object getDetails() {
                        return userDetails;
                    }
                    @Override
                    public Object getPrincipal() {
                        return userDetails;
                    }
                    @Override
                    public boolean isAuthenticated() {
                        return true;
                    }
                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
                    @Override
                    public String getName() {
                        return userDetails.getUsername();
                    }
                }));
        return session;
    }
}