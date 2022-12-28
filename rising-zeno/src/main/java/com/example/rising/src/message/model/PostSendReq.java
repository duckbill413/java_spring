package com.example.rising.src.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSendReq {
    @NotNull
    private Long postIdx;
    @NotNull
    private String message;
}
