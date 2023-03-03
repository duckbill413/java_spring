package com.example.workbook.dto;

/**
 * author        : duckbill413
 * date          : 2023-03-03
 * description   :
 **/

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardImageDTO {
    private String uuid;
    private String fileName;
    private int ord;
}
