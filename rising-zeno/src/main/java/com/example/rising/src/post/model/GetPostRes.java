package com.example.rising.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPostRes {
    private String title;
    private String content;
    private Category category;
    private Long price;
    private List<String> imageUrls;
}
