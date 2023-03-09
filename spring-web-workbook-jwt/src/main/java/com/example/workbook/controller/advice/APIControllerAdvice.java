package com.example.workbook.controller.advice;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author        : duckbill413
 * date          : 2023-03-09
 * description   :
 **/
@RestControllerAdvice
@RequiredArgsConstructor
public class APIControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exceptionHandler(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        Gson gson = new Gson();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        allErrors.forEach(objectError -> {
            errors.put(objectError.getCode(), objectError.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(gson.toJson(errors));
    }
}
