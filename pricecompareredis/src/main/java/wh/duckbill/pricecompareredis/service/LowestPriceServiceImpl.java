package wh.duckbill.pricecompareredis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import wh.duckbill.pricecompareredis.vo.Keyword;
import wh.duckbill.pricecompareredis.vo.Product;
import wh.duckbill.pricecompareredis.vo.ProductGrp;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {
    private final RedisTemplate<String, Object> myProdPriceRedis;

    /**
     * ZSet에 저장된 정보를 조회
     * rangeWithScores는 value와 rank를 한번에 조회
     * rangeWithScores의 파라미터는
     * key: redis에서 검색하는 key 값
     * first: key 값에 대한 value 중 fist 부터
     * second: key 값에 대한 value 중 second 까지 조회
     * @param key Redis 검색하는 키 값
     * @return Redis에 저장된 데이터
     */
    @Override
    public Set<?> getZSetValue(String key) {
        return myProdPriceRedis.opsForZSet().rangeWithScores(key, 0, 9);
    }

    /**
     * 새로운 데이터를 Redis에 삽입
     * ZSet을 사용하기 때문에 rank를 기준으로 정렬되어 삽입된다.
     * add의 파라미터
     * first: key 값
     * second: value 값
     * third: rank 비교를 위한 가중치
     * @param newProduct Redis에 저장하고자 하는 상품 데이터
     * @return 저장한 데이터의 인덱스(rank)
     */
    @Override
    public int setNewProduct(Product newProduct) {
        myProdPriceRedis.opsForZSet().add(newProduct.getProductGrpId(), newProduct.getProductId(), newProduct.getPrice());
        return myProdPriceRedis.opsForZSet().rank(newProduct.getProductGrpId(), newProduct.getProductId()).intValue();
    }

    @Override
    public int setNewProductGrp(ProductGrp newProductGrp) {
        newProductGrp.getProductList().forEach(product -> myProdPriceRedis.opsForZSet().add(newProductGrp.getProdGrpId(), product.getProductId(), product.getPrice()));
        return myProdPriceRedis.opsForZSet().zCard(newProductGrp.getProdGrpId()).intValue();
    }

    @Override
    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
        myProdPriceRedis.opsForZSet().add(keyword, prodGrpId, score);
        return myProdPriceRedis.opsForZSet().rank(keyword, prodGrpId).intValue();
    }

    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return null;
    }
}
