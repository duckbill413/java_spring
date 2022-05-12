package spring2.zenospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2.zenospring.model.*;
import java.util.List;

@Service
public class UserProvider {

    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao){
        this.userDao = userDao;
    }

    public List<GetUserRes> getUser(){
        List<GetUserRes> userRes = userDao.userRes();
        return userRes;
    }

    public PostUserRes postUser(PostUserReq postUserReq){
        int userIdx = userDao.addUser(postUserReq);
        PostUserRes postUserRes = new PostUserRes(userIdx);
        return postUserRes;
    }
}
