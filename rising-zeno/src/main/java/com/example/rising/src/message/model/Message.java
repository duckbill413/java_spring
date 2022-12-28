package com.example.rising.src.message.model;

import com.example.rising.src.post.model.Post;
import com.example.rising.src.user.model.Users;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private Long messageIdx;
    private String message;
    private Post post;
    private Users sender;
    private Users receiver;
}
