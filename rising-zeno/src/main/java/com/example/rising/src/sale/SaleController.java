package com.example.rising.src.sale;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponse;
import com.example.rising.config.BaseResponseStatus;
import com.example.rising.src.sale.model.PostSalesRes;
import com.example.rising.src.sale.model.PutSoldReq;
import com.example.rising.src.sale.model.PutSoldRes;
import com.example.rising.src.sale.model.SoldStatus;
import com.example.rising.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final JwtService jwtService;
    private final SaleService saleService;

    @ApiOperation(value = "Item 거래 내역 등록")
    @PutMapping("/sold")
    public BaseResponse<PutSoldRes> itemSold(@Valid @RequestBody PutSoldReq putSoldReq) {
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
    @ApiOperation(value = "유저의 판매 상품 출력")
    @GetMapping("/sell")
    public BaseResponse<List<PostSalesRes>> findSelling(){
        try {
            long userIdxByJwt = jwtService.getUserIdx();
            List<PostSalesRes> sellList = saleService.findSelling(userIdxByJwt);
            return new BaseResponse<>(sellList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    @ApiOperation(value = "유저의 구매 상품 출력")
    @GetMapping("/buy")
    public BaseResponse<List<PostSalesRes>> findBuying(){
        try {
            long userIdxByJwt = jwtService.getUserIdx();
            List<PostSalesRes> buyList = saleService.findBuying(userIdxByJwt);
            return new BaseResponse<>(buyList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
