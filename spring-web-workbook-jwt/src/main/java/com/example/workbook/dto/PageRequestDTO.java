package com.example.workbook.dto;

/**
 * author        : duckbill413
 * date          : 2023-03-07
 * description   :
 **/

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    private String keyword;
    private LocalDate from;
    private LocalDate to;
    private Boolean completed;
    public Pageable getPageable(String... props){
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }
}
