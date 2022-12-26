package com.example.risingtest.security;

import com.example.risingtest.base.BaseException;
import com.example.risingtest.base.BaseResponseStatus;
import com.example.risingtest.base.BaseRole;
import com.example.risingtest.domain.Member;
import com.example.risingtest.repository.MemberRepository;
import com.example.risingtest.security.dto.APIUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class APIMemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">>> USERNAME: " + username);
        Optional<Member> result = memberRepository.findByMid(username);
        Member member = result.orElseThrow(() -> new UsernameNotFoundException("유저 아이디 값을 확인해주세요."));

        APIUserDTO dto = new APIUserDTO(
                member.getMid(),
                member.getMpw(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        return dto;
    }
}
