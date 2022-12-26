package com.example.risingtest.utill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate() {
        Map<String, Object> claimMap = Map.of("mid", "ABCDE");
        String jwtStr = jwtUtil.generateToken(claimMap, 1);
        System.out.println(">>> jwtStr: " + jwtStr);
    }

    @Test
    public void testValidate() {
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzE5NjgwMTMsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNjcxOTY3OTUzfQ.O_E3tcdOf5IIbFfCEXHsddY3jKTI_XvGft-dB434gOQ";
        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
        System.out.println(claim);
    }

    @Test
    public void testAll(){
        String jwtStr = jwtUtil.generateToken(
                Map.of("mid", "AAAA", "email", "aaaa@bbb.com"),1);
        System.out.println(">>> jwtStr : " + jwtStr);
        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
        System.out.println(">>> MID : " + claim.get("mid"));

        System.out.println(">>> EMAIL : " + claim.get("email"));
    }
}