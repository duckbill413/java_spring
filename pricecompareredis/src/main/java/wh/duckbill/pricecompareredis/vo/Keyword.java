package wh.duckbill.pricecompareredis.vo;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {
    private String keyword; // 유아용품 - 하기스귀저기, A사 딸랑이
    private List<ProductGrp> productGrpList; // {"FPG0001", "FPG0002"}
}
