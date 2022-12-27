package com.example.rising.src.user;

import com.example.rising.config.BindingException;
import com.example.rising.src.user.model.*;
import com.example.rising.config.BaseResponse;
import com.example.rising.config.BaseException;
import com.example.rising.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Log4j2
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserProvider userProvider;
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 회원가입 API
     * [POST] /api/user/sign-up
     */
    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> createUser(@Valid @RequestBody PostUserReq postUserReq,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse<>(new BindingException().message(bindingResult));
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인 API
     * [POST] /api/user/log-in
     */
    @ApiOperation("회원 로그인")
    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> logIn(@Valid @RequestBody PostLoginReq postLoginReq,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse<>(new BindingException().message(bindingResult));
        }
        try {
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 비밀번호 변경 API
     * [PATCH] /api/user/revise/password
     */
    @ApiOperation("비밀번호 변경")
    @PatchMapping("/revise/password")
    public BaseResponse<String> modifyUserPwd(@Valid @RequestBody PatchPwdReq patchPwdReq,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse<>(new BindingException().message(bindingResult));
        }
        try {
            //jwt에서 idx 추출.
            long userIdxByJwt = jwtService.getUserIdx();

            userService.modifyUserPwd(userIdxByJwt, patchPwdReq.getNewPassword());

            String result = "비밀번호가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 정보 조회
     * [POST] /api/user/info
     */
    @ApiOperation("유저 정보 조회")
    @PostMapping("/info")
    public BaseResponse<User> getUserInfo(@Valid @RequestBody PostUserInfoReq postUserInfoReq,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse<>(new BindingException().message(bindingResult));
        }
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            PostLoginReq postLoginReq = new PostLoginReq(postUserInfoReq.getEmail(), postUserInfoReq.getPassword());
            userProvider.logIn(postLoginReq);
            User findUser = userProvider.getUserInfo(userIdxByJwt);
            return new BaseResponse<>(findUser);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
