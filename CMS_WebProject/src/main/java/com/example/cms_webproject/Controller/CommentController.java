package com.example.cms_webproject.Controller;

/*import io.swagger.annotations.ApiOperation;*/
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.cms_webproject.Dto.CommentDto;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Repository.UserRepository;
import com.example.cms_webproject.Response;
import com.example.cms_webproject.Service.CommentService;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    // 댓글 작성
    /*@ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")*/
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comments/{boardId}")
    public Response writeComment(@PathVariable("boardId") Integer boardId, @RequestBody CommentDto commentDto) {
        return new Response("성공", "댓글 작성을 완료했습니다.", commentService.writeComment(boardId, commentDto));
    }


    // 게시글에 달린 댓글 모두 불러오기
    /*@ApiOperation(value = "댓글 불러오기", notes = "게시글에 달린 댓글을 모두 불러온다.")*/
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{boardId}")
    public Response getComments(@PathVariable("boardId") Integer boardId) {
        return new Response("성공", "댓글을 불러왔습니다.", commentService.getComments(boardId));
    }


    // 댓글 삭제
    /*@ApiOperation(value = "댓글 삭제", notes = "게시글에 달린 댓글을 삭제합니다.")*/
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/comments/{boardId}/{commentId}")
    public Response deleteComment(@PathVariable("boardId") Integer boardId, @PathVariable("commentId") Integer commentId) {
        // 추후 JWT 로그인 기능을 추가하고나서, 세션에 로그인된 유저와 댓글 작성자를 비교해서, 맞으면 삭제 진행하고
        // 틀리다면 예외처리를 해주면 된다.

        return new Response("성공", "댓글 삭제 완료", commentService.deleteComment(commentId));
    }

}
