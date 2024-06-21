package wh.duckbill.pricecompareredis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wh.duckbill.pricecompareredis.service.LowestPriceService;
import wh.duckbill.pricecompareredis.vo.Keyword;
import wh.duckbill.pricecompareredis.vo.NotFoundException;
import wh.duckbill.pricecompareredis.vo.Product;
import wh.duckbill.pricecompareredis.vo.ProductGrp;

import java.util.HashSet;
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

    @GetMapping("/product1")
    public Set GetZsetValueWithStatus(String key) {
        try {
            return lowestPriceService.GetZsetValueWithStatus(key);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping("/product2")
    public Set GetZsetValueUsingExController(String key) throws Exception {
        try {
            return lowestPriceService.GetZsetValueWithStatus(key);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    @GetMapping("/product3")
    public ResponseEntity<Set> GetZsetValueUsingExControllerWithSpecificException(String key) throws Exception {
        Set<String> mySet = new HashSet<>();
        try {
            mySet = lowestPriceService.GetZsetValueWithSpecificException(key);
        } catch (NotFoundException ex) {
            throw new Exception(ex);
        }
        HttpHeaders responseHeaders = new HttpHeaders();

        return new ResponseEntity<>(mySet, responseHeaders, HttpStatus.OK);
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
