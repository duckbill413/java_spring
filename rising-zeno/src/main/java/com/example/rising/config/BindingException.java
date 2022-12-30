package com.example.rising.config;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class BindingException {

    public static String message(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getAllErrors().forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;
            String message = fieldError.getDefaultMessage();

            sb.append(fieldError.getField() + " " + message);
        });
        return sb.toString();
    }
}
