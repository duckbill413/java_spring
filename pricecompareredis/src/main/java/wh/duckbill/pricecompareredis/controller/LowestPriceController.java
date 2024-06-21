package wh.duckbill.pricecompareredis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wh.duckbill.pricecompareredis.service.LowestPriceService;
import wh.duckbill.pricecompareredis.vo.Keyword;
import wh.duckbill.pricecompareredis.vo.Product;
import wh.duckbill.pricecompareredis.vo.ProductGrp;

import java.util.Set;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LowestPriceController {
    private final LowestPriceService lowestPriceService;

    @GetMapping("/getZSetValue")
    public Set<?> GetZSetValue(@RequestParam String key) {
        return lowestPriceService.getZSetValue(key);
    }

    @PutMapping("/product")
    public int SetNewProduct(@RequestBody Product newProduct) {
        return lowestPriceService.setNewProduct(newProduct);
    }

    @PutMapping("/productGroup")
    public int SetNewProductGrp(@RequestBody ProductGrp newProductGrp) {
        return lowestPriceService.setNewProductGrp(newProductGrp);
    }

    @PutMapping("/productGrpToKeyword")
    public int setNewProductGrpToKeyword(
            @RequestParam String keyword,
            @RequestParam String prodGrpId,
            @RequestParam double score
    ) {
        return lowestPriceService.setNewProductGrpToKeyword(keyword, prodGrpId, score);
    }

    @GetMapping("/productPrice/lowest")
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return lowestPriceService.getLowestPriceProductByKeyword(keyword);
    }
}
