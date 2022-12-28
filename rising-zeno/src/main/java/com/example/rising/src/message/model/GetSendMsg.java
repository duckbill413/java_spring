package com.example.rising.src.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSendMsg {
    private Long postIdx;
    private Long receiver;
    private String message;
}
