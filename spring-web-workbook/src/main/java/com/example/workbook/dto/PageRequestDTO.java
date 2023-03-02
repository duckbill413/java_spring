package com.example.workbook.dto;

/**
 * author        : duckbill413
 * date          : 2023-02-27
 * description   : 목록/검색 처리
 * 검색의 종류는 문자열 하나로 처리해서 나중에 각 문자를 분리하도록 구성
 *
 * 페이징 관련 정보(page/size) 외에 검색의 종류(type)와 키워드(keyword)를 추가하여 저장
 **/

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    private String type; // 검색의 종류 t, c, w, tc, tw, twc (동적 쿼리 Querydsl)
    private String keyword;
    private String link;

    /**
     * Get type string.
     * type으로 넘어온 검색 조건을 배열로 변경
     * @return the string [ ]
     */
    public String[] getTypes() {
        if (type == null || type.isEmpty())
            return null;

        return type.split("");
    }
    public Pageable getPageable(String... props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    /**
     * Get link string.
     * 검색 조건과 페이징 조건 등을 문자열로 구성
     * @return the string
     */
    public String getLink(){
        if (link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page);
            builder.append("&size="+this.size);

            if (Strings.isNotBlank(this.type)){
                builder.append("&type="+type);
            }

            if (Strings.isNotEmpty(keyword)){
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
                }
            }

            this.link = builder.toString();
        }
        log.info("PageRequestDTO link: " + link);
        return this.link;
    }
}
