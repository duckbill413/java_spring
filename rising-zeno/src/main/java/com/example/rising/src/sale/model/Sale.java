package com.example.rising.src.sale.model;

import com.example.rising.src.post.model.Post;
import com.example.rising.src.user.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale {
    private Long saleIdx;
    private String status;
    private Post post;
}
