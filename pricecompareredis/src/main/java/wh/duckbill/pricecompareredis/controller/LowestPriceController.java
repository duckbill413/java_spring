package wh.duckbill.pricecompareredis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wh.duckbill.pricecompareredis.service.LowestPriceService;
import wh.duckbill.pricecompareredis.vo.Product;

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
}
