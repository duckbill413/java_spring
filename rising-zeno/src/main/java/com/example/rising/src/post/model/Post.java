package com.example.rising.src.post.model;

import com.example.rising.src.img.model.Image;
import com.example.rising.src.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx;
    private String title;
    private String content;
    private String category;
    private Long price;
    @OneToMany(mappedBy = "post")
    private List<Image> images = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;
}
