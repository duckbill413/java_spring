package com.example.workbook.controller;

import com.example.workbook.dto.APIUserDTO;
import com.example.workbook.service.APIUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * author        : duckbill413
 * date          : 2023-03-09
 * description   :
 **/
@RestController
@RequestMapping("/apiUser")
@RequiredArgsConstructor
public class APIUserController {
    private final APIUserService apiUserService;
    @PostMapping("/")
    public Map<String, String> register(@RequestBody APIUserDTO apiUserDTO){
        String mid = apiUserService.register(apiUserDTO);

        return Map.of("mid", mid);
    }

    @GetMapping("/{mid}")
    public APIUserDTO read(@PathVariable("mid") String mid){
        return apiUserService.read(mid);
    }

    @PutMapping("/{mid}")
    public Map<String, String> modify(@PathVariable("mid") String mid,
                                      @RequestBody APIUserDTO apiUserDTO){
        apiUserService.modify(mid, apiUserDTO);

        return Map.of("result","success");
    }
    @DeleteMapping("/{mid}")
    public Map<String, String> delete(@PathVariable("mid") String mid){
        apiUserService.read(mid);

        return Map.of("result", "success");
    }
}
