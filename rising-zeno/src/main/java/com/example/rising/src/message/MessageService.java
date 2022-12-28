package com.example.rising.src.message;

import com.example.rising.config.BaseException;
import com.example.rising.src.message.model.GetReceiveMsg;
import com.example.rising.src.message.model.GetSendMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.rising.config.BaseResponseStatus.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageDao messageDao;
    public void sendMessage(long postIdx, long sender, long receiver, String message) throws BaseException{
        try {
            messageDao.sendMessage(postIdx, sender, receiver, message);
        } catch (Exception e){
            throw new BaseException(SEND_MESSAGE_FAILED);
        }
    }

    public List<GetReceiveMsg> received(long postIdx, long userIdx) throws BaseException {
        try {
            List<GetReceiveMsg> result = messageDao.received(postIdx, userIdx);
            return result;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSendMsg> mailed(long postIdx, long userIdx) throws BaseException {
        try {
            List<GetSendMsg> result = messageDao.mailed(postIdx, userIdx);
            return result;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
