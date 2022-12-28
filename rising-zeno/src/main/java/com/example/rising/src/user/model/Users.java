package com.example.rising.src.user.model;

import com.example.rising.src.heart.model.Heart;
import com.example.rising.src.post.model.Post;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Users {
    private Long usersIdx;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String nickname;
    private String profileImg;
}
