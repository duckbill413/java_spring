package com.example.rising.src.heart;

import com.example.rising.src.heart.model.GetHeartStatus;
import com.example.rising.src.post.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HeartDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Post> showHeartedPost(long userIdx) {
        String query = "select p.post_idx as post_idx, p.title as title, p.content as content, p.category as category, p.price as price " +
                "from post p inner join heart h " +
                "on p.post_idx = h.post_idx where h.status=true and p.users_idx=?";
        String param = String.valueOf(userIdx);
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> Post.builder()
                        .postIdx(rs.getLong("post_idx"))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .category(rs.getString("category"))
                        .price(rs.getLong("price")).build()
                , param);
    }

    public void toggleHeart(long userIdx, long postIdx) {
        int exists = heartExists(userIdx, postIdx);
        // 하트가 존재하지 않는 경우
        if (exists == 0) {
            String query = "INSERT INTO heart (status, post_idx, users_idx) VALUES (true, ?, ?)";
            Object[] params = new Object[]{postIdx, userIdx};
            this.jdbcTemplate.update(query, params);
        }
        else {
            GetHeartStatus getHeartStatus = heartStatus(userIdx, postIdx);
            String status = getHeartStatus.isStatus() ? "false" : "true";

            String query = "UPDATE heart SET status = ? WHERE heart_idx = ?";
            Object[] params = new Object[]{status, getHeartStatus.getHeartIdx()};
            this.jdbcTemplate.update(query, params);
        }
    }

    private int heartExists(long userIdx, long postIdx) {
        String query = "SELECT EXISTS(SELECT heart_idx FROM heart WHERE users_idx = ? AND post_idx = ?)";
        Object[] params = new Object[]{userIdx, postIdx};
        return this.jdbcTemplate.queryForObject(query, int.class, params);
    }

    private GetHeartStatus heartStatus(long userIdx, long postIdx){
        String query = "SELECT heart_idx, status FROM heart WHERE users_idx = ? AND post_idx = ?";
        Object[] params = new Object[]{userIdx, postIdx};
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> GetHeartStatus.builder()
                        .heartIdx(rs.getLong("heart_idx"))
                        .status(rs.getBoolean("status")).build(), params);
    }
}
