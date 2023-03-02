package com.example.workbook.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-03-02
 * description   :
 **/
@Data
public class UploadFileDTO {
    private List<MultipartFile> files;
}
