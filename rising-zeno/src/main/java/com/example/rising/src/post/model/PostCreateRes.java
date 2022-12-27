package com.example.rising.src.post.model;

import com.example.rising.src.img.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateRes {
    private Long postIdx;
}
