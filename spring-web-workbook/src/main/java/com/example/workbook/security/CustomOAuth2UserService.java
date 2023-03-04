package com.example.workbook.security;

import com.example.workbook.dto.Member;
import com.example.workbook.dto.MemberRole;
import com.example.workbook.repository.MemberRepository;
import com.example.workbook.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
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
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final String socialDefaultPwd = "1111";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = switch (clientName){
            case "kakao" -> getKakaoEmail(paramMap);
            default -> throw new OAuth2AuthenticationException("Not Support Social Login");
        };

        return generateDTO(email, paramMap);
    }

    private MemberSecurityDTO generateDTO(String email, Map<String, Object> paramMap) {
        Optional<Member> result = memberRepository.findByEmail(email);

        // DB에 사용자 없으면 회원 가입
        if (result.isEmpty()){
            Member member = Member.builder()
                    .mid(email)
                    .mpw(passwordEncoder.encode(socialDefaultPwd))
                    .email(email)
                    .social(true)
                    .build();
            member.addRole(MemberRole.USER);
            memberRepository.save(member);

            // MemberSecurityDTO 구성 및 반환
            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                    email, socialDefaultPwd, email, false, true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            memberSecurityDTO.setProps(paramMap);

            return memberSecurityDTO;
        }

        Member member = result.get();
        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(), member.getMpw(), member.getEmail(), member.isDel(), member.isSocial(),
                member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
        );
        return memberSecurityDTO;
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
        Object value = paramMap.get("kakao_account");

        LinkedHashMap accountMap = (LinkedHashMap) value;

        String email = (String) accountMap.get("email");

        return email;
    }
}
