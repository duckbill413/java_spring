package com.example.rising.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatchPwdReq {
    @NotNull(message = "비밀번호가 입력되지 않았습니다.")
    @Size(min = 6, message = "비밀번호는 6자리 이상이여야 합니다.")
    private String newPassword;
}
