package com.example.cms_webproject.Dto;

import com.example.cms_webproject.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cms_webproject.Model.Board;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long ordersBoard; //게시글 번호
    private String title;
    private String contents;
    private String subject;
    private Long orders; //유저 번호

    public static BoardDto toDto(Board board) {
        return new BoardDto(
                board.getOrdersBoard(),
                board.getTitle(),
                board.getContents(),
                board.getSubject(),
                board.getUser().getOrders());
    }
}