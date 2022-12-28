package com.example.rising.src.sale;

import com.example.rising.src.post.PostDao;
import com.example.rising.src.post.model.FindPostRes;
import com.example.rising.src.post.model.Post;
import com.example.rising.src.sale.model.PutSoldRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SaleDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PutSoldRes matchingWithUsers(long postIdx, long buyer, String status) {
        long saleIdx = findSaleIdx(postIdx);
        String findPostWriterQuery = "SELECT users_idx FROM post WHERE post_idx = ?";
        String findPostWriterParam = String.valueOf(postIdx);

        long seller =  this.jdbcTemplate.queryForObject(findPostWriterQuery, long.class,
                findPostWriterParam);

        String query = "UPDATE sale SET status = ?, seller = ?, buyer = ? WHERE sale_idx = ?";
        Object[] params = new Object[]{status, seller, buyer, saleIdx};
        this.jdbcTemplate.update(query, params);

        String findQuery = "SELECT * FROM sale WHERE sale_idx = ?";
        String findParam = String.valueOf(saleIdx);
        return this.jdbcTemplate.queryForObject(findQuery,
                (rs, rowNum) -> PutSoldRes.builder()
                        .buyer(rs.getLong("buyer"))
                        .seller(rs.getLong("seller"))
                        .postIdx(rs.getLong("post_idx"))
                        .status(rs.getString("status")).build()
                , findParam);
    }

    public long findSaleIdx(long postIdx) {
        String query = "SELECT sale_idx FROM sale WHERE post_idx = ?";
        String param = String.valueOf(postIdx);
        return this.jdbcTemplate.queryForObject(query, long.class, param);
    }

    public List<Post> findSelling(long userIdx){
        String query = "select p.post_idx as post_idx, p.title as title, p.content as content,\n" +
                "       p.category as category, p.price as price\n" +
                "from post as p join sale s on p.post_idx = s.post_idx\n" +
                "where s.status='ON_SALE' and s.seller = ?";
        String param = String.valueOf(userIdx);
        return this.jdbcTemplate.query(query, (rs, rowNum) -> Post.builder()
                .postIdx(rs.getLong("post_idx"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .category(rs.getString("category"))
                .price(rs.getLong("price")).build(), param);
    }

    public List<Post> findBuying(long userIdx){
        String query = "select p.post_idx as post_idx, p.title as title, p.content as content,\n" +
                "       p.category as category, p.price as price\n" +
                "from post as p join sale s on p.post_idx = s.post_idx\n" +
                "where s.status='ON_SALE' and s.buyer = ?";
        String param = String.valueOf(userIdx);
        return this.jdbcTemplate.query(query, (rs, rowNum) -> Post.builder()
                .postIdx(rs.getLong("post_idx"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .category(rs.getString("category"))
                .price(rs.getLong("price")).build(), param);
    }
}
