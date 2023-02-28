package com.example.workbook.controller.sample;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author        : duckbill413
 * date          : 2023-02-25
 * description   :
 **/
@RestController
@Log4j2
public class SampleJSONController {
    @GetMapping("/helloArr")
    public String[] helloArr() {
        log.info("helloArr.................");
        return new String[]{"AAA", "BBB", "CCC"};
    }
}
