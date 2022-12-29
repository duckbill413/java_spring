package com.example.rising.src.heart;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponseStatus;
import com.example.rising.src.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartDao heartDao;
    public List<Post> showHeartedPost(long userIdx) throws BaseException {
        try {
            List<Post> posts = heartDao.showHeartedPost(userIdx);
            return posts;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public String toggleHeart(long userIdx, long postIdx) throws BaseException {
        try {
            String result = heartDao.toggleHeart(userIdx, postIdx);
            return result;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
