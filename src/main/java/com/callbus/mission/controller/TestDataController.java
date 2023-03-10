package com.callbus.mission.controller;

import com.callbus.mission.dto.AccountType;
import com.callbus.mission.dto.PostDTO;
import com.callbus.mission.entity.Member;
import com.callbus.mission.dto.Quit;
import com.callbus.mission.entity.Post;
import com.callbus.mission.service.MemberService;
import com.callbus.mission.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestDataController {

    private final MemberService memberService;
    private final PostService postService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 테스트를 위한 멤버 등록 api
    private Long useLimit = 0L;
    private Long postLimit = 0L;

    @GetMapping("/test/register/member")
    public ResponseEntity<?> saveMember() {
        log.info("### MemberController.saveMember 실행");

        if (useLimit == 0) {
            String password = bCryptPasswordEncoder.encode("임대인1234");
            Member member = new Member("임대인", AccountType.LESSOR, "임대인ID", password, Quit.REGISTER);

            String password2 = bCryptPasswordEncoder.encode("임차인1234");
            Member member2 = new Member("임차인 ", AccountType.LESSEE, "임차인ID", password2, Quit.REGISTER);

            String password3 = bCryptPasswordEncoder.encode("공인중개사1234");
            Member member3 = new Member("공인중개사", AccountType.REALTOR, "공인중개사ID", password3, Quit.REGISTER);

            memberService.save(member);
            memberService.save(member2);
            memberService.save(member3);
            useLimit += 1;

            log.info("ID:임대인ID, PWD:임대인1234");
            log.info("ID:임차인ID, PWD:임차인1234");
            log.info("ID:공인중개사ID, PWD:공인중개사1234");
            log.info("멤버 등록 완료");
            return new ResponseEntity<>(("OK"), HttpStatus.OK);
        }
        return new ResponseEntity<>(("이미 실행하였습니다."), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test/register/post")
    public ResponseEntity<?> savePost() {
        log.info("### MemberController.savePost 실행");

        if (useLimit != 1) {
            return new ResponseEntity<>(("Member 등록을 먼저 해주시기 바랍니다"), HttpStatus.BAD_REQUEST);
        }
        if (useLimit == 1 && postLimit == 0) {
            Member member = memberService.findById(1L);

            PostDTO.Request.Save postDto = new PostDTO.Request.Save("title", "content");
            Post post = new Post(postDto, member);
            PostDTO.Request.Save postDto2 = new PostDTO.Request.Save("title2", "content2");
            Post post2 = new Post(postDto2, member);
            PostDTO.Request.Save postDto3 = new PostDTO.Request.Save("title3", "content3");
            Post post3 = new Post(postDto3, member);

            PostDTO.Request.Save postDto4 = new PostDTO.Request.Save("title3", "content3");
            Post post4 = new Post(postDto4, member);

            PostDTO.Request.Save postDto5 = new PostDTO.Request.Save("title3", "content3");
            Post post5 = new Post(postDto5, member);

            PostDTO.Request.Save postDto6 = new PostDTO.Request.Save("title3", "content3");
            Post post6 = new Post(postDto6, member);

            PostDTO.Request.Save postDto7 = new PostDTO.Request.Save("title3", "content3");
            Post post7 = new Post(postDto7, member);

            PostDTO.Request.Save postDto8 = new PostDTO.Request.Save("title3", "content3");
            Post post8 = new Post(postDto8, member);

            postService.save(post);
            postService.save(post2);
            postService.save(post3);
            postService.save(post4);
            postService.save(post5);
            postService.save(post6);
            postService.save(post7);
            postService.save(post8);

            log.info("게시글 등록 완료");
            return new ResponseEntity<>(("OK"), HttpStatus.OK);
        }
        return new ResponseEntity<>(("이미 실행하였습니다."), HttpStatus.BAD_REQUEST);
    }
}
