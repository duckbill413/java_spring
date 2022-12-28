package com.example.rising.src.message;

import com.example.rising.src.message.model.GetReceiveMsg;
import com.example.rising.src.message.model.GetSendMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MessageDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void sendMessage(long postIdx, long sender, long receiver, String message) {
        String sendMessageQuery = "INSERT INTO message (post_idx, sender, receiver, message) VALUES (?, ?, ?, ?)";
        Object[] sendMessageParams = new Object[]{postIdx, sender, receiver, message};
        this.jdbcTemplate.update(sendMessageQuery, sendMessageParams);
    }

    public List<GetReceiveMsg> received(long postIdx, long userIdx) {
        String query = "SELECT * FROM message WHERE post_idx = ? AND receiver = ?";
        Object[] params = new Object[]{postIdx, userIdx};

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> GetReceiveMsg.builder()
                        .postIdx(rs.getLong("post_idx"))
                        .sender(rs.getLong("sender"))
                        .message(rs.getString("message"))
                        .build(), params);
    }

    public List<GetSendMsg> mailed(long postIdx, long userIdx) {
        String query = "SELECT * FROM message WHERE post_idx = ? AND sender = ?";
        Object[] params = new Object[]{postIdx, userIdx};

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> GetSendMsg.builder()
                        .postIdx(rs.getLong("post_idx"))
                        .receiver(rs.getLong("receiver"))
                        .message(rs.getString("message"))
                        .build(), params);
    }
}
