package com.example.rising.src.sale;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponse;
import com.example.rising.config.BaseResponseStatus;
import com.example.rising.src.post.model.Post;
import com.example.rising.src.sale.model.PutSoldReq;
import com.example.rising.src.sale.model.PutSoldRes;
import com.example.rising.src.sale.model.SoldStatus;
import com.example.rising.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final JwtService jwtService;
    private final SaleService saleService;

    @PutMapping("/sold")
    public BaseResponse<PutSoldRes> itemSold(@RequestBody PutSoldReq putSoldReq) {
        try {
            try {
                SoldStatus.valueOf(putSoldReq.getStatus());
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.SALE_CATEGORY_INVALID);
            }
            long userIdxByJwt = jwtService.getUserIdx();
            PutSoldRes putSoldRes = saleService.matchingWithUsers(putSoldReq.getPostIdx(), userIdxByJwt, putSoldReq.getStatus());
            return new BaseResponse<>(putSoldRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/sell")
    public BaseResponse<List<Post>> findSelling(){
        try {
            long userIdxByJwt = jwtService.getUserIdx();
            List<Post> sellList = saleService.findSelling(userIdxByJwt);
            return new BaseResponse<>(sellList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/buy")
    public BaseResponse<List<Post>> findBuying(){
        try {
            long userIdxByJwt = jwtService.getUserIdx();
            List<Post> buyList = saleService.findBuying(userIdxByJwt);
            return new BaseResponse<>(buyList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
