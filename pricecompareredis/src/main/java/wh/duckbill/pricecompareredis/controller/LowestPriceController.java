package wh.duckbill.pricecompareredis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.pricecompareredis.service.LowestPriceService;

import java.util.Set;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LowestPriceController {
    private final LowestPriceService lowestPriceService;

    @GetMapping("/getZSetValue")
    public Set<?> GetZSetValue(String key) {
        return lowestPriceService.getZSetValue(key);
    }
}
