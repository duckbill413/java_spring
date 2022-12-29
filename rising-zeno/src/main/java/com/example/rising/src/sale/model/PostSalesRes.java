package com.example.rising.src.sale.model;

import com.example.rising.src.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSalesRes {
    private Post post;
    private String status;
}
