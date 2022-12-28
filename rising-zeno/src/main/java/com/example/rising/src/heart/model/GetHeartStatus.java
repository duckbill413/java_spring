package com.example.rising.src.heart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetHeartStatus {
    private Long heartIdx;
    private boolean status;
}
