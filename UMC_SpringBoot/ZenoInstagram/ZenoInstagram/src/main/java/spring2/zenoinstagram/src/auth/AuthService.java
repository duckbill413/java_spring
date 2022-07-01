package spring2.zenoinstagram.src.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2.zenoinstagram.config.BaseException;
import spring2.zenoinstagram.utils.JwtService;
import spring2.zenoinstagram.src.auth.model.*;
import spring2.zenoinstagram.src.auth.*;
import spring2.zenoinstagram.utils.SHA256;

import static spring2.zenoinstagram.config.BaseResponseStatus.*;


@Service
public class AuthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthDao authDao;
    private final AuthProvider authProvider;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthDao authDao, AuthProvider authProvider, JwtService jwtService){
        this.authDao = authDao;
        this.authProvider = authProvider;
        this.jwtService = jwtService;
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        User user = authDao.getPwd(postLoginReq);
        String encryptPwd;

        try{
            encryptPwd = new SHA256().encrypt(postLoginReq.getPwd());
        }
        catch (Exception e){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR)
        }
    }
}
