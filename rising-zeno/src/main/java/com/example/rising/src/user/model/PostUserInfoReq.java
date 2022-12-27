package com.example.rising.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUserInfoReq {
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotNull
    private String email;
    @NotNull
    private String password;
}
