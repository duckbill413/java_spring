package wh.duckbill.pricecompareredis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wh.duckbill.pricecompareredis.vo.Keyword;
import wh.duckbill.pricecompareredis.vo.NotFoundException;
import wh.duckbill.pricecompareredis.vo.Product;
import wh.duckbill.pricecompareredis.vo.ProductGrp;

import java.util.*;
import java.util.stream.Collectors;

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
        myProdPriceRedis.opsForZSet().add(newProduct.getProdGrpId(), newProduct.getProductId(), newProduct.getPrice());
        return myProdPriceRedis.opsForZSet().rank(newProduct.getProdGrpId(), newProduct.getProductId()).intValue();
    }

    @Override
    public Set GetZsetValueWithStatus(String key) throws Exception {
        Set myTempSet = myProdPriceRedis.opsForZSet().rangeWithScores(key, 0, 9);
        if (myTempSet.isEmpty()) {
            throw new Exception("The Key doesn't have any member");
        }
        return myTempSet;
    }

    @Override
    public Set GetZsetValueWithSpecificException(String key) throws Exception {
        Set myTempSet = myProdPriceRedis.opsForZSet().rangeWithScores(key, 0, 9);
        if (myTempSet.isEmpty()) {
            throw new NotFoundException("The Key doesn't exist in redis", HttpStatus.NOT_FOUND);
        }
        return myTempSet;
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

    /**
     * keyword를 통해 product group 가져오기 (10개)
     * product group으로 product:price 가져오기 (10개)
     * 가져온 정보들을 return 할 obj에 넣기
     * @param keyword 검색하는 keyword
     * @return Keyword 검색에 대한 리스트
     */
    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        Keyword returnInfo = new Keyword();
        List<ProductGrp> tempProdGrp = new ArrayList<>();
        // keyword 를 통해 ProductGroup 가져오기 (10개)
        tempProdGrp = getProdGrpUsingKeyword(keyword);

        // 가져온 정보들을 Return 할 Object 에 넣기
        returnInfo.setKeyword(keyword);
        returnInfo.setProductGrpList(tempProdGrp);
        // 해당 Object return
        return returnInfo;
    }

    public List<ProductGrp> getProdGrpUsingKeyword(String keyword) {
        List<ProductGrp> returnInfo = new ArrayList<>();

        // input 받은 keyword 로 productGrpId를 조회
        Set<String> prodGrpIdList = myProdPriceRedis.opsForZSet().reverseRange(keyword, 0, 9)
                .stream().map(Object::toString).collect(Collectors.toSet());

        //Product tempProduct = new Product();
        List<Product> tempProdList = new ArrayList<>();

        //10개 prodGrpId로 loop
        for (final String prodGrpId : prodGrpIdList) {
            // Loop 타면서 ProductGrpID 로 Product:price 가져오기 (10개)

            ProductGrp tempProdGrp = new ProductGrp();

            Set prodAndPriceList = myProdPriceRedis.opsForZSet().rangeWithScores(prodGrpId, 0, 9);
            Iterator<Object> prodPricObj = prodAndPriceList.iterator();

            // loop 타면서 product obj에 bind (10개)
            while (prodPricObj.hasNext()) {
                ObjectMapper objMapper = new ObjectMapper();
                // {"value":00-10111-}, {"score":11000}
                Map<String, Object> prodPriceMap = objMapper.convertValue(prodPricObj.next(), Map.class);
                Product tempProduct = new Product();
                // Product Obj bind
                tempProduct.setProductId(prodPriceMap.get("value").toString()); // prod_id
                tempProduct.setPrice(Double.valueOf(prodPriceMap.get("score").toString()).intValue()); //es 검색된 score
                tempProduct.setProdGrpId(prodGrpId);

                tempProdList.add(tempProduct);
            }
            // 10개 product price 입력완료
            tempProdGrp.setProdGrpId(prodGrpId);
            tempProdGrp.setProductList(tempProdList);
            returnInfo.add(tempProdGrp);
        }

        return returnInfo;
    }
}
