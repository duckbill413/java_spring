package com.example.workbook.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/
@SpringBootTest
@Log4j2
class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;

    @DisplayName("generate jwt")
    @Test
    public void generateJwtTest() {
        Map<String, Object> claim = Map.of(
                "mid", "AAAA",
                "mpw", "aaaa"
        );

        String jwtStr = jwtUtil.generateToken(claim, 1);
        log.info(jwtStr);
    }

    @DisplayName("validate jwt token test")
    @Test
    public void testValidate() {
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtaWQiOiJBQUFBIiwiZXhwIjoxNjc4MDI1OTE1LCJtcHciOiJhYWFhIiwiaWF0IjoxNjc4MDI1Nzk1fQ.l59quq98hTzJADY_yc2s7COzh6QG7QCFL7MFrMl3tZ0";

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
        log.info(claim);
    }

}