package com.example.rising.src.post;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponseStatus;
import com.example.rising.src.post.model.FindPostRes;
import com.example.rising.src.post.model.GetPostRes;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostProvider {
    private final PostDao postDao;

    public List<GetPostRes> getPosts(long page) throws BaseException {
        try{
            List<GetPostRes> posts = postDao.getPosts(page);
            return posts;
        } catch (BaseException e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkPostExists(long postIdx) throws BaseException {
        try {
            return postDao.checkPostExists(postIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public FindPostRes findPostWriter(long postIdx) throws BaseException {
        try {
            return postDao.findPostWriter(postIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
