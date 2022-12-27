package com.example.rising.src.img.model;

import com.example.rising.src.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgIdx;
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "post_fk")
    private Post post;
}
