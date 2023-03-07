package com.example.workbook.repository;

import com.example.workbook.domain.Member;
import com.example.workbook.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * author        : duckbill413
 * date          : 2023-03-04
 * description   :
 **/
@SpringBootTest
@Log4j2
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @DisplayName("Member 삽입 테스트")
    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Member member = Member.builder()
                    .mid("member"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@aaa.bbb")
                    .build();

            member.addRole(MemberRole.USER);

            if (i >= 90){
                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }
    @DisplayName("Member 조회 테스트")
    @Test
    public void testRead() {
        Optional<Member> result = memberRepository.getWithRoles("member100");

        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }
    @Commit
    @DisplayName("소셜로그인 비밀번호 업데이트 테스트")
    @Test
    public void testUpdate() {
        String mid = "";
        String mpw = passwordEncoder.encode("54321");
        memberRepository.updatePassword(mpw, mid);
    }
}