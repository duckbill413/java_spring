package com.example.rising.src.user;

import com.example.rising.src.user.model.*;
import com.example.rising.config.BaseException;
import com.example.rising.utils.JwtService;
import com.example.rising.utils.AES128;
import com.example.rising.config.secret.Secret;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.rising.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserProvider {
    private final UserDao userDao;
    private final JwtService jwtService;

    // 로그인(password 검사)
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = userDao.getPwd(postLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword()); // 암호화
            // 회원가입할 때 비밀번호가 암호화되어 저장되었기 떄문에 로그인을 할때도 암호화된 값끼리 비교를 해야합니다.
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if (postLoginReq.getPassword().equals(password)) { //비밀번호가 일치한다면 userIdx를 가져온다.
            Long userIdx = userDao.getPwd(postLoginReq).getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx,jwt);
        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    // 이메일 중복 확인
    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 닉네임 중복 확인
    public int checkNickname(String nickname) throws BaseException {
        try {
            return userDao.checkNickname(nickname);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 userIdx를 갖는 User의 정보 조회
    public User getUserInfo(long userIdx) throws BaseException {
        try {
            User findUser = userDao.getUser(userIdx);
            return findUser;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
