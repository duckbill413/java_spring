package com.example.rising.src.post.model;

import com.example.rising.src.img.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateReq {
    @NotNull(message = "제목을 입력해주세요.")
    @Column(nullable = false)
    private String title;
    @NotNull(message = "내용을 적어주세요.")
    @Column(nullable = false)
    private String content;
    @NotNull(message = "카테고리를 선택해주세요.")
    private String category;
    @NotNull
    private Long price;
    @NotNull
    private List<String> imageUrls;
}
