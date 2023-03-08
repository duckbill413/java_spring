package com.example.workbook.security;

import com.example.workbook.domain.APIUser;
import com.example.workbook.domain.APIUserRole;
import com.example.workbook.repository.APIUserRepository;
import com.example.workbook.security.dto.APIUserSecurityDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author        : duckbill413
 * date          : 2023-03-08
 * description   :
 **/
@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final APIUserRepository apiUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("-------------------CustomOauth2UserService-----------------------");
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> paramMap = oAuth2User.getAttributes();
        System.out.println("-----------------");
        System.out.println(paramMap);

        Map<String, String> socialUserDetails = switch (clientName) {
            case "kakao" -> getKakaoEmail(paramMap);
            case "naver" -> getNaverEmail(paramMap);
            default -> null;
        };

        return generateDTO(socialUserDetails, paramMap);
    }

    private APIUserSecurityDTO generateDTO(Map<String, String> socialUserDetails, Map<String, Object> paramMap) {
        String email = socialUserDetails.get("email");
        Optional<APIUser> result = apiUserRepository.findByMid(email);

        // 데이터베이스에 해당 이메일 사용자가 없다면
        if (result.isEmpty()) {
            String randomPassword = UUID.randomUUID().toString();

            APIUser apiUser = APIUser.builder().mid(email).mpw(passwordEncoder.encode(randomPassword)).email(email).social(true).build();
            apiUser.addRole(APIUserRole.USER);
            apiUserRepository.save(apiUser);

            // APIUserSecurityDTO 구성 및 반환
            APIUserSecurityDTO apiUserSecurityDTO = new APIUserSecurityDTO(email, randomPassword, email, false, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            apiUserSecurityDTO.setProps(paramMap);

            return apiUserSecurityDTO;
        } else {
            APIUser apiUser = result.get();
            APIUserSecurityDTO apiUserSecurityDTO = new APIUserSecurityDTO(apiUser.getEmail(), apiUser.getMpw(), apiUser.getEmail(), apiUser.isDeleted(), apiUser.isSocial(), apiUser.getRoleSet().stream().map(apiUserRole -> new SimpleGrantedAuthority("ROLE_" + apiUserRole.name())).collect(Collectors.toList()));

            return apiUserSecurityDTO;
        }
    }

    private Map<String, String> getKakaoEmail(Map<String, Object> paramMap) {
        Object value = paramMap.get("kakao_account");
        LinkedHashMap accountMap = (LinkedHashMap) value;

        return Map.of(
                "email", (String) accountMap.get("email")
        );
    }

    private Map<String, String> getNaverEmail(Map<String, Object> paramMap) {
        Object value = paramMap.get("response");
        LinkedHashMap accountMap = (LinkedHashMap) value;

        return Map.of(
                "email", (String) accountMap.get("email"),
                "nickname", (String) accountMap.get("nickname")
        );
    }
}
