package com.example.cms_webproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cms_webproject.Model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByBoardOrders(int boardId);
}
