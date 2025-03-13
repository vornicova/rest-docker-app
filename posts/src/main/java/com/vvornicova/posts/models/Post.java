package com.vvornicova.posts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
    private Long id;
    private Long userId;    // привязка к пользователю
    private String title;
    private String content;
}
