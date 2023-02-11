package com.callbus.mission.config.auth;

import com.callbus.mission.entity.Member;
import com.callbus.mission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        log.info("nickName 로그인 시도={}",accountId);
        Optional<Member> findMember = memberRepository.findByAccountId(accountId);
        return findMember.map(PrincipalDetails::new).orElse(null);
    }
}
