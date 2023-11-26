package com.example.cms_webproject.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.cms_webproject.Dto.CommentDto;
import com.example.cms_webproject.Model.Board;
import com.example.cms_webproject.Model.Comment;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Repository.BoardRepository;
import com.example.cms_webproject.Repository.CommentRepository;
import com.example.cms_webproject.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 댓글 작성하기
    @Transactional
    public CommentDto writeComment(int boardId, CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        // 입력으로 받은 user_id로 User 엔터티를 찾음
        User user = userRepository.findByOrders(commentDto.getOrders())
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));


        // 게시판 번호로 게시글 찾기
        Board board = boardRepository.findById((long) boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });

        comment.setUser(user);
        comment.setBoard(board);
        commentRepository.save(comment);

        return CommentDto.toDto(comment);
    }



    // 글에 해당하는 전체 댓글 불러오기
    @Transactional(readOnly = true)
    public List<CommentDto> getComments(int boardId) {
        List<Comment> comments = commentRepository.findAllByBoardOrdersBoard((long) boardId);
        List<CommentDto> commentDtos = new ArrayList<>();

        comments.forEach(s -> commentDtos.add(CommentDto.toDto(s)));
        return commentDtos;
    }


    // 댓글 삭제하기
    @Transactional
    public String deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> {
            return new IllegalArgumentException("댓글 Id를 찾을 수 없습니다.");
        });
        commentRepository.deleteById(commentId);
        return "삭제 완료";
    }
}
