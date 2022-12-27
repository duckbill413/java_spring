package com.example.rising.src.user;

import com.example.rising.config.BaseException;
import com.example.rising.config.secret.Secret;
import com.example.rising.src.user.model.*;
import com.example.rising.utils.AES128;
import com.example.rising.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.example.rising.config.BaseResponseStatus.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    // 회원가입(POST)
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        String pwd;
        try {
            if (userProvider.checkEmail(postUserReq.getEmail()) == 1)
                throw new BaseException(POST_USERS_EXISTS_EMAIL);
            if (userProvider.checkNickname(postUserReq.getNickname()) == 1)
                throw new BaseException(POST_USERS_EXISTS_NICKNAME);

            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword()); // 암호화코드
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try {
            Long userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 비밀번호 수정(Patch)
    public void modifyUserPwd(Long userId, String newPassword) throws BaseException {
        try {
            String encryptedPod = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(newPassword);
            int result = userDao.modifyUserPwd(userId, encryptedPod); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
