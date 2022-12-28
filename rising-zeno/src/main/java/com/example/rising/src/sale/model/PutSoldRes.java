package com.example.rising.src.sale.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutSoldRes {
    private Long postIdx;
    private String status;
    private Long seller;
    private Long buyer;
}
