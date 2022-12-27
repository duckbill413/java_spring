package com.example.rising.src.post;

import com.example.rising.config.BaseException;
import com.example.rising.src.post.model.Category;
import com.example.rising.src.post.model.GetPostRes;
import com.example.rising.src.post.model.PostCreateReq;
import com.example.rising.src.img.model.Image;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@Log4j2
@Repository
public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long createPost(long userIdx, PostCreateReq postCreateReq) {
        String createPostQuery = "insert into post (title, content, category, price, user_fk) values(?, ?, ?, ?, ?)";
        Object[] createPostParams = new Object[]{postCreateReq.getTitle(), postCreateReq.getContent(),
                postCreateReq.getCategory(), postCreateReq.getPrice(), userIdx};
        this.jdbcTemplate.update(createPostQuery, createPostParams);
        String lastInsertIdQuery = "select last_insert_id()";
        long postIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);

        List<String> images = postCreateReq.getImageUrls();
        String createImageQuery = "insert into image (img_url, post_fk) values (?, ?)";
        images.forEach(image -> {
            Object[] createImageParams = new Object[]{image, postIdx};
            this.jdbcTemplate.update(createImageQuery, createImageParams);
        });

        return postIdx;
    }

    public List<GetPostRes> getPosts(long page) throws BaseException {
        long start = page * 3;
        String getPostsQuery = "SELECT * FROM post ORDER BY post_idx DESC LIMIT ?, 3";

        return this.jdbcTemplate.query(getPostsQuery, (rs, rowNum) -> GetPostRes.builder()
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .category(Enum.valueOf(Category.class, rs.getString("category")))
                .price(rs.getLong("price"))
                .imageUrls(
                        this.jdbcTemplate.query("SELECT img_url FROM image WHERE post_fk = ?",
                                        (rk, rw) -> rk.getString("img_url"),
                                        rs.getLong("post_idx")))
                .build(),
                start);
    }
}
