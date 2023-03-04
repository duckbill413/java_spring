package com.example.workbook.security;

import com.example.workbook.dto.Member;
import com.example.workbook.repository.MemberRepository;
import com.example.workbook.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author        : duckbill413
 * date          : 2023-03-04
 * description   :
 **/
@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.getWithRoles(username);

        if (result.isEmpty()){
            throw new UsernameNotFoundException("user not found...");
        }

        Member member = result.get();
        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(member.getMid(), member.getMpw(),
                member.getEmail(), member.isDel(), member.isSocial(),
                member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList()));

        return memberSecurityDTO;
    }
}
