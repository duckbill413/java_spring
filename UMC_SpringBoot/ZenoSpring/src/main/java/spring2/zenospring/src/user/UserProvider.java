package spring2.zenospring.src.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2.zenospring.config.BaseException;
import spring2.zenospring.src.user.model.DeleteUserRes;
import spring2.zenospring.src.user.model.GetUserRes;
import spring2.zenospring.utils.JwtService;

import static spring2.zenospring.config.BaseResponseStatus.DATABASE_ERROR;
import static spring2.zenospring.config.BaseResponseStatus.USERS_EMPTY_USER_IDX;
import java.util.List;
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

    public List<GetUserRes> getUsers() throws BaseException{
        try {
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes getUsersByEmail(String email) throws BaseException {
        try {
            GetUserRes getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
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

    public int checkUserIdx(int userIdx) throws BaseException{
        try{
            return userDao.checkUserIdx(userIdx);
        } catch(Exception exception){
            throw new BaseException((DATABASE_ERROR));
        }
    }
}
