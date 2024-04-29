package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cms_webproject.Model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByBoardOrdersBoard(Long boardOrders);

    List<Comment> findByBoard(Board board);
}
