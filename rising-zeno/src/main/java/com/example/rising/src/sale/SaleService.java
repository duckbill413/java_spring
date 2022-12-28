package com.example.rising.src.sale;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponseStatus;
import com.example.rising.src.post.model.Post;
import com.example.rising.src.sale.model.PutSoldRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleDao saleDao;
    public PutSoldRes matchingWithUsers(long postIdx, long buyer, String status) throws BaseException {
        try {
            PutSoldRes putSoldRes = saleDao.matchingWithUsers(postIdx, buyer, status);
            return putSoldRes;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public List<Post> findSelling(long userIdx) throws BaseException {
        try {
            List<Post> sellList = saleDao.findSelling(userIdx);
            return sellList;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<Post> findBuying(long userIdx) throws BaseException {
        try {
            List<Post> buyList = saleDao.findBuying(userIdx);
            return buyList;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
