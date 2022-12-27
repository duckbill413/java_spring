package com.example.rising.src.user.model;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUserReq {
    @Email(message = "이메일 형식이 일치하지 않습니다.")
    @NotNull(message = "이메일이 입력되지 않았습니다.")
    private String email;
    @Size(min = 6, message = "비밀번호는 6자리 이상이여야 합니다.")
    @NotNull(message = "비밀번호가 입력되지 않았습니다.")
    private String password;
    @NotNull(message = "전화번호가 비어 있습니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호를 확인 해주세요. (형식: XXX-XXXX-XXXX)")
    private String phoneNumber;
    @NotNull(message = "닉네임이 입력되지 않았습니다.")
    private String nickname;
    private String profileImg;
}
