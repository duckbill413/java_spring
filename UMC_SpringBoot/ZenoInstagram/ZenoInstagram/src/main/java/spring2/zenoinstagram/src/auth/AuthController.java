package spring2.zenoinstagram.src.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring2.zenoinstagram.config.BaseException;
import spring2.zenoinstagram.config.BaseResponse;
import spring2.zenoinstagram.config.BaseResponseStatus;
import spring2.zenoinstagram.src.auth.model.*;
import spring2.zenoinstagram.utils.JwtService;

import java.security.AuthProvider;
import java.util.List;

import static spring2.zenoinstagram.config.BaseResponseStatus.*;
import static spring2.zenoinstagram.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AuthProvider authProvider;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final JwtService jwtService;

    public AuthController(AuthProvider authProvider, AuthService authService, JwtService jwtService {
        this.authProvider = authProvider;
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * 로그인 API
     * [GET] /posts/{userIdx}
     *
     * @param userIdx
     * @return BaseResponse<List < GetPostsRes>>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        try {
            if(postLoginReq.getEmail()==null)
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            if(postLoginReq.getPwd()==null)
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            if(!isRegexEmail(postLoginReq.getEmail()))
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);

            PostLoginRes postLoginRes = authService.logIn(postLoginReq);

            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
