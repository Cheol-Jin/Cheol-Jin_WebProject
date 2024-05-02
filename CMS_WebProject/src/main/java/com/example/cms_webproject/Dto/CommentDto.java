package com.example.cms_webproject.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cms_webproject.Model.Comment;

@Data
public class CommentDto {
    private String content;
    private Long UserOrders;

    public CommentDto(String content, Long UserOrders) {
        this.content = content;
        this.UserOrders = UserOrders;
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getContent(),
                comment.getUser().getOrders()
        );
    }
}
