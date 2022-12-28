package com.example.rising.src.post.model;

import com.example.rising.src.heart.model.Heart;
import com.example.rising.src.img.model.Image;
import com.example.rising.src.message.model.Message;
import com.example.rising.src.sale.model.Sale;
import com.example.rising.src.user.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private Long postIdx;
    private String title;
    private String content;
    private String category;
    private Long price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Image> images = new ArrayList<>();
}
