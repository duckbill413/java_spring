package com.example.rising.src.heart;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponse;
import com.example.rising.src.post.model.Post;
import com.example.rising.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final JwtService jwtService;

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

    @PostMapping("/toggle/{post-idx}")
    public BaseResponse<String> toggleHeart(@PathVariable(name = "post-idx") long postIdx) {
        try {
            long userIdxJwt = jwtService.getUserIdx();
            heartService.toggleHeart(userIdxJwt, postIdx);
            return new BaseResponse<>("하트 리스트에 추가 되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
