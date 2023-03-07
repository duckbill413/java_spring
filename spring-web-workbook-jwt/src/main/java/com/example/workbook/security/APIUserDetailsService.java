package com.example.workbook.security;

import com.example.workbook.domain.APIUser;
import com.example.workbook.dto.APIUserDTO;
import com.example.workbook.repository.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/
@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {
    private final APIUserRepository apiUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<APIUser> result = apiUserRepository.getWithRoles(username);

        APIUser apiUser = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find mid"));

        APIUserDTO dto = new APIUserDTO(
                apiUser.getMid(),
                apiUser.getMpw(),
                apiUser.getRoleSet().stream().map(apiUserRole ->
                        new SimpleGrantedAuthority("ROLE_"+apiUserRole.name())).collect(Collectors.toList())
        );
        log.info(dto);
        return dto;
    }
}
