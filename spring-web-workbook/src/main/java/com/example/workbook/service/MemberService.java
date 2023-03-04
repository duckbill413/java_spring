package com.example.workbook.service;

import com.example.workbook.dto.MemberJoinDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * author        : duckbill413
 * date          : 2023-03-04
 * description   :
 **/
public interface MemberService {
    static class MidExistException extends Exception{}

    void join(MemberJoinDTO memberJoinDTO)throws MidExistException;
}
