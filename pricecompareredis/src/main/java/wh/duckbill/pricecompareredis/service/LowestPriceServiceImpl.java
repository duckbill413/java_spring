package wh.duckbill.pricecompareredis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {
    @Override
    public Set<?> getZSetValue(String key) {
        return Set.of();
    }
}
