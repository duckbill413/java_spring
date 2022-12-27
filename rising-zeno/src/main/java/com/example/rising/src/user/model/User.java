package com.example.rising.src.user.model;

import com.example.rising.src.post.model.Post;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;
    private String email;
    private String password;
    private String phoneNumber;
    private String nickname;
    private String profileImg;
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
}
