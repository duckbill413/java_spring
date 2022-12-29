package com.example.rising.src.post;

import com.example.rising.config.BaseException;
import com.example.rising.src.post.model.*;
import com.example.rising.src.img.model.Image;
import com.example.rising.src.sale.SaleDao;
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
        // post 생성
        String createPostQuery = "insert into post (title, content, category, price, users_idx) values(?, ?, ?, ?, ?)";
        Object[] createPostParams = new Object[]{postCreateReq.getTitle(), postCreateReq.getContent(),
                postCreateReq.getCategory(), postCreateReq.getPrice(), userIdx};
        this.jdbcTemplate.update(createPostQuery, createPostParams);

        // image 생성
        String lastInsertIdQuery = "select last_insert_id()";
        long postIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
        List<String> images = postCreateReq.getImageUrls();
        String createImageQuery = "insert into image (img_url, post_fk) values (?, ?)";
        images.forEach(image -> {
            Object[] createImageParams = new Object[]{image, postIdx};
            this.jdbcTemplate.update(createImageQuery, createImageParams);
        });

        // sale 생성
        String query = "INSERT INTO sale (post_idx, status, seller) VALUES (?, 'ON_SALE', ?)";
        Object [] params = new Object[]{postIdx, userIdx};
        this.jdbcTemplate.update(query, params);

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

    public int checkPostExists(long postIdx){
        String checkPostQuery = "SELECT EXISTS (SELECT post_idx FROM post WHERE post_idx = ?)";
        String checkPostParam = String.valueOf(postIdx);
        return this.jdbcTemplate.queryForObject(checkPostQuery, int.class, checkPostParam);
    }

    public FindPostRes findPostWriter(long postIdx){
        String findPostWriterQuery = "SELECT * FROM post WHERE post_idx = ?";
        String findPostWriterParam = String.valueOf(postIdx);

        return this.jdbcTemplate.queryForObject(findPostWriterQuery,
                (rs, rowNum) -> FindPostRes.builder()
                        .postIdx(rs.getLong("post_idx"))
                        .usersIdx(rs.getLong("users_idx"))
                        .build(),
                findPostWriterParam);
    }
}
