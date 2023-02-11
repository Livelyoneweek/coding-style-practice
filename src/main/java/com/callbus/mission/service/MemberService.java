package com.callbus.mission.service;

import com.callbus.mission.entity.Member;
import com.callbus.mission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id가 없습니다"));
    }
}
