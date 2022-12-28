package com.example.rising.src.heart.model;

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
public class Heart {
    private Long heartIdx;
    private boolean status; // true: 하트 상태, false: 하트 취소
    private Post post;
    private Users users;
}
