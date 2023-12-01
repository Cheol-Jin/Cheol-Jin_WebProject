package com.example.cms_webproject.Controller;

/*import io.swagger.annotations.ApiOperation;*/
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.cms_webproject.Dto.BoardDto;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Repository.UserRepository;
import com.example.cms_webproject.Response;
import com.example.cms_webproject.Service.BoardService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;


    // 전체 게시글 조회
   /* @ApiOperation(value = "전체 게시글 보기", notes = "전체 게시글을 조회한다.")*/
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards")
    public Response getBoards() {
        return new Response("성공", "전체 게시물 리턴", boardService.getBoards());
    }



    // 개별 게시글 조회
   /* @ApiOperation(value = "개별 게시글 보기", notes = "개별 게시글 조회한다.")*/
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards/{orders}")
    public Response getBoard(@PathVariable("orders") Long orders) {
        return new Response("성공", "개별 게시물 리턴", boardService.getBoard(orders));
    }



    // 게시글 작성
   /* @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")*/
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/boards/write")
    public Response write(@RequestBody BoardDto boardDto) {
        return new Response("성공", "글 작성 성공", boardService.write(boardDto));
    }



    // 게시글 수정
   /* @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")*/
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/boards/update/{orders}")
    public Response edit(@RequestBody BoardDto boardDto, @PathVariable("orders") Long id) {
        return new Response("성공", "글 수정 성공", boardService.update(id, boardDto));
    }




    // 게시글 삭제
    /*@ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")*/
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/boards/delete/{orders}")
    public Response delete(@PathVariable("orders") Long id) {
        boardService.delete(id); // 이 메소드는 반환값이 없으므로 따로 삭제 수행해주고, 리턴에는 null을 넣어줌
        return new Response("성공", "글 삭제 성공", null);
    }
}
