package com.example.rising.src.heart;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponse;
import com.example.rising.src.post.model.Post;
import com.example.rising.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final JwtService jwtService;

    /**
     * 유저의 하트 누른 게시물 확인하기
     * [POST] /api/heart/show
     */
    @ApiOperation(value = "하트 누른 게시물 확인")
    @GetMapping("/show")
    public BaseResponse<List<Post>> showHeartedPost() {
        try {
            long userIdxJwt = jwtService.getUserIdx();
            List<Post> posts = heartService.showHeartedPost(userIdxJwt);
            return new BaseResponse<>(posts);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 게시물에 하트 누르기
     * [POST] /api/heart/toggle/{post-idx}
     * 이미 하트 처리된 게시물을 다시 한번 처리하면 하트가 비활성화 된다.
     * @param postIdx
     * @return
     */
    @ApiOperation(value = "게시물에 하트 누르기")
    @PostMapping("/toggle/{post-idx}")
    public BaseResponse<String> toggleHeart(@PathVariable(name = "post-idx") long postIdx) {
        try {
            long userIdxJwt = jwtService.getUserIdx();
            String result = heartService.toggleHeart(userIdxJwt, postIdx);
            return new BaseResponse<>(true, 1000, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
