package com.example.rising.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status;  //BaseResponseStatus 객체에 매핑
    public void message(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getAllErrors().forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;
            String message = fieldError.getDefaultMessage();
            sb.append(fieldError.getField() + " " + message);
        });

        this.status = BaseResponseStatus.DATABASE_ERROR;
    }
}
