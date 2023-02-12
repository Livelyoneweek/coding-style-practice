package com.callbus.mission.service;

import com.callbus.mission.dto.AccountType;
import com.callbus.mission.dto.Quit;
import com.callbus.mission.entity.Member;
import com.callbus.mission.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Member Save 테스트")
    void save() {
        //given
        String password = bCryptPasswordEncoder.encode("임대인1234");
        Member member = new Member("임대인", AccountType.LESSOR, "임대인ID", password, Quit.REGISTER);

        String password2 = bCryptPasswordEncoder.encode("임차인1234");
        Member member2 = new Member("임차인 ", AccountType.LESSEE, "임차인ID", password2, Quit.REGISTER);

        String password3 = bCryptPasswordEncoder.encode("공인중개사1234");
        Member member3 = new Member("공인중개사", AccountType.REALTOR, "공인중개사ID", password3, Quit.REGISTER);

        //when
        memberService.save(member);
        memberService.save(member2);
        memberService.save(member3);

        //then
        Member findMember = memberRepository.findById(member.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        Member findMember3 = memberRepository.findById(member3.getId()).get();
        assertEquals(member,findMember);
        assertEquals(member2,findMember2);
        assertEquals(member3,findMember3);
    }

    @Test
    @DisplayName("Member findById exception 테스트")
    void findById() {
        //when
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            memberService.findById(1L);
        });

        //then
        assertEquals("해당 id가 없습니다", exception.getMessage());
    }
}