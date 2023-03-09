package com.example.workbook.service;

import com.example.workbook.dto.APIUserDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * author        : duckbill413
 * date          : 2023-03-09
 * description   :
 **/
@Transactional
public interface APIUserService {
    String register(APIUserDTO apiUserDTO);
    APIUserDTO read(String mid);
    void modify(String mid, APIUserDTO apiUserDTO);
    void remove(String mid);
}
