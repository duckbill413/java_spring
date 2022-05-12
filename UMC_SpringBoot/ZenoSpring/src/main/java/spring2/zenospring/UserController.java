package spring2.zenospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring2.zenospring.model.*;
import java.util.List;

@RestController
public class UserController {
    private UserProvider userProvider;

    @Autowired
    public UserController(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @GetMapping("/users")
    public List<GetUserRes> getUser(){
        List<GetUserRes> userRes = userProvider.getUser();
        return userRes;
    }

    @ResponseBody
    @PostMapping("/users")
    public PostUserRes postUser(@RequestBody PostUserReq postUserReq){
        PostUserRes postUserRes = userProvider.postUser(postUserReq);
        return postUserRes;
    }
}
