package com.example.cms_webproject.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cms_webproject.Model.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String content;
    private Long orders;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getOrders()
        );
    }
}
