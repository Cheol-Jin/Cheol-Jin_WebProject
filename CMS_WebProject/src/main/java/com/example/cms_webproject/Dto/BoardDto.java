package com.example.cms_webproject.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cms_webproject.Model.Board;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int id;
    private String title;
    private String contents;
    private String writer;
    private String subject;

    public static BoardDto toDto(Board board) {
        return new BoardDto(
                board.getOrders(),
                board.getTitle(),
                board.getContents(),
                board.getSubject(),
                board.getUser().getId());
    }

}