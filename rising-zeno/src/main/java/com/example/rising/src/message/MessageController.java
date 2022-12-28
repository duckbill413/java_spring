package com.example.rising.src.message;

import com.example.rising.config.BaseException;
import com.example.rising.config.BaseResponse;
import com.example.rising.config.BaseResponseStatus;
import com.example.rising.config.BindingException;
import com.example.rising.src.message.model.GetReceiveMsg;
import com.example.rising.src.message.model.GetSendMsg;
import com.example.rising.src.message.model.PostSendReq;
import com.example.rising.src.post.PostProvider;
import com.example.rising.src.post.model.FindPostRes;
import com.example.rising.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final PostProvider postProvider;
    private final JwtService jwtService;
    private final MessageService messageService;
    @ApiOperation(value = "메시지 보내기")
    @PostMapping("/send")
    public BaseResponse<String> sendMessage(@Valid @RequestBody PostSendReq postSendReq,
                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new BaseResponse<>(new BindingException().message(bindingResult));
        }

        try{
            // 게시물이 존재하지 않는 경우 에러
            if (postProvider.checkPostExists(postSendReq.getPostIdx()) == 0)
                throw new BaseException(BaseResponseStatus.POST_NOT_EXISTS);

            FindPostRes findPostRes = postProvider.findPostWriter(postSendReq.getPostIdx());
            long userIdxByJwt = jwtService.getUserIdx();
            messageService.sendMessage(findPostRes.getPostIdx(), userIdxByJwt, findPostRes.getUsersIdx(), postSendReq.getMessage());

            return new BaseResponse<>("메세지가 발송 되었습니다.");
        } catch (BaseException e){
            return new BaseResponse<String>(e.getStatus());
        }
    }

    @ApiOperation(value = "수신 메시지 확인")
    @GetMapping("/receive/{post-idx}")
    public BaseResponse<List<GetReceiveMsg>> receivedMessage(@PathVariable("post-idx") long postIdx){
        try {
            long userIdx = jwtService.getUserIdx();
            List<GetReceiveMsg> messages = messageService.received(postIdx, userIdx);
            return new BaseResponse<>(messages);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    @ApiOperation(value = "송신 메시지 확인")
    @GetMapping("/mail/{post-idx}")
    public BaseResponse<List<GetSendMsg>> mailMessage(@PathVariable("post-idx") long postIdx){
        try {
            long userIdx = jwtService.getUserIdx();
            List<GetSendMsg> messages = messageService.mailed(postIdx, userIdx);
            return new BaseResponse<>(messages);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
