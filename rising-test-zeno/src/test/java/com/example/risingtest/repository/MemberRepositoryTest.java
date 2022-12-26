package com.example.risingtest.repository;

import com.example.risingtest.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void test(){
        IntStream.rangeClosed(1, 10).forEach(value -> {
            Member member = Member.builder()
                    .mid("memberId" + value)
                    .mpw(passwordEncoder.encode("1111"))
                    .build();
            memberRepository.save(member);
        });

        memberRepository.findAll().forEach(System.out::println);
    }
}