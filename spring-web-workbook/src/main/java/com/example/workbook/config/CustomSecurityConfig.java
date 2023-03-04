package com.example.workbook.config;

import com.example.workbook.security.CustomUserDetailsService;
import com.example.workbook.security.handler.Custom403Handler;
import com.example.workbook.security.handler.CustomSocialLoginSuccessHandler;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * author        : duckbill413
 * date          : 2023-03-04
 * description   :
 **/
@Log4j2
@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("------------configure-------------------");
        //커스텀 로그인 페이지
        http.formLogin().loginPage("/member/login").permitAll()
                .defaultSuccessUrl("/board/list");
        http.logout();
        //CSRF 토큰 비활성화
        http.csrf().disable();
        http.cors().disable();
        // 로그인 없이 접근시 로그인 페이지로 리다이렉트
        // /board/list url은 permit all
        http.authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/member/join/**").permitAll()
                .requestMatchers("/board/list/**").permitAll()
                .anyRequest().authenticated()
        );
        // remember-me 자동로그인 설정
        http.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60*60*24*30);

        // 403 에러에 대한 handler 부착
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        // Oauth2 Login
        http.oauth2Login().loginPage("/member/login").permitAll()
                .successHandler(authenticationSuccessHandler());

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new Custom403Handler();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }
}
