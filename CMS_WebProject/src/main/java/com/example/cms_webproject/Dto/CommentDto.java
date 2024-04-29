package com.example.cms_webproject.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cms_webproject.Model.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int comment_id;
    private String content;
    private Long UserOrders;
    private Long boardOrders;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getComment_id(),
                comment.getContent(),
                comment.getUser().getOrders(),
                comment.getBoard().getOrdersBoard()
        );
    }
}
