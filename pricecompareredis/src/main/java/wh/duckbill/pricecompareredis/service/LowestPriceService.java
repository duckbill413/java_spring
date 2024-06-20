package wh.duckbill.pricecompareredis.service;

import wh.duckbill.pricecompareredis.vo.Product;

import java.util.Set;

public interface LowestPriceService {
    Set<?> getZSetValue(String key);

    int setNewProduct(Product newProduct);
}
