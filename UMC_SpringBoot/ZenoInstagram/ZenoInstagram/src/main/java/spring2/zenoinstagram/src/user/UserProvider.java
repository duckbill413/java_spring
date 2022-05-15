package spring2.zenoinstagram.src.user;


import spring2.zenoinstagram.config.BaseException;
import spring2.zenoinstagram.src.user.model.GetUserRes;
import spring2.zenoinstagram.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static spring2.zenoinstagram.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }


    public GetUserRes getUsersByEmail(String email) throws BaseException {
        try {
            GetUserRes getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getAllUsers() throws BaseException {
        try{
            List<GetUserRes> getAllUsers = userDao.getUsers();
            return getAllUsers;
        } catch (Exception e){
            throw  new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserRes getUsersByIdx(int userIdx) throws BaseException {
        try {
            GetUserRes getUsersRes = userDao.getUsersByIdx(userIdx);
            return getUsersRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNickName(String nickName) throws BaseException {
        try {
            return userDao.checkNickName(nickName);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkPhone(String phone) throws BaseException {
        try {
            return userDao.checkPhone(phone);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkPassword(String email, String password) throws BaseException {
        try {
            return userDao.checkUserPassword(email, password);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
