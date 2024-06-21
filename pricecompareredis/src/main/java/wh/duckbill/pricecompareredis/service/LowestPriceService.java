package wh.duckbill.pricecompareredis.service;

import wh.duckbill.pricecompareredis.vo.Keyword;
import wh.duckbill.pricecompareredis.vo.Product;
import wh.duckbill.pricecompareredis.vo.ProductGrp;

import java.util.Set;

public interface LowestPriceService {
    Set<?> getZSetValue(String key);

    int setNewProduct(Product newProduct);

    int setNewProductGrp(ProductGrp newProductGrp);

    int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score);

    Keyword getLowestPriceProductByKeyword(String keyword);
}
