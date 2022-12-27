package com.example.rising.src.post;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponseStatus;
import com.example.rising.src.post.model.PostCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;

    public Long createPost(Long userIdx, PostCreateReq postCreateReq) throws BaseException {
        try{
            Long postIdx = postDao.createPost(userIdx, postCreateReq);
            return postIdx;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
