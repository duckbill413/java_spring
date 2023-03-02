package com.example.workbook.dto.upload;

/**
 * author        : duckbill413
 * date          : 2023-03-02
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
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean img;

    public String getLink() {
        if (img) {
            return "s_" + uuid + "_" + fileName; // 이미지의 경우 썸네일
        } else {
            return uuid + "_" + fileName;
        }
    }
}
