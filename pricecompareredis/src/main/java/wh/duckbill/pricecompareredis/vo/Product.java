package wh.duckbill.pricecompareredis.vo;

import lombok.Data;

@Data
public class Product {
    private String productGrpId; // FPG0001
    private String productId;
    private int price; // 25000
}
