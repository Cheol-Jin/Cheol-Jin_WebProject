package com.example.cms_webproject.Service;

import com.example.cms_webproject.Dto.BasketInfoDto;
import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Repository.BasketRepository;
import com.example.cms_webproject.Repository.MaterialRepository;
import com.example.cms_webproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.cms_webproject.Dto.BoardDto;
import com.example.cms_webproject.Model.Board;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Repository.BoardRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RequiredArgsConstructor
@Service
public class BoardService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(BoardService.class);

    private final BoardRepository boardRepository;
    private final BasketRepository basketRepository;
    private final MaterialRepository materialRepository;
    private final UserRepository userRepository;

    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public List<BoardDto> getBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        boards.forEach(s -> boardDtos.add(BoardDto.toDto(s)));
        return boardDtos;
    }

    // 개별 게시물 조회
    @Transactional(readOnly = true)
    public BoardDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다.");
        });
        BoardDto boardDto = BoardDto.toDto(board);
        return boardDto;
    }

    // 게시물 작성
    @Transactional(propagation = Propagation.REQUIRED)
    public BoardDto write(BoardDto boardDto) {
        // 게시글 생성
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContents(boardDto.getContents());
        board.setSubject(boardDto.getSubject());

        // 사용자 정보 설정
        User user = userRepository.findByOrders(boardDto.getOrders())
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        board.setUser(user);

        // 게시글과 관련된 장바구니 정보 가져오기
        List<Object[]> basketInfoList = basketRepository.getBasketInfoByUserOrder(boardDto.getOrders());

        // 각 장바구니 정보에 대해 Board와 Basket 생성 및 저장
        for (Object[] basketInfo : basketInfoList) {
            // Basket 생성 및 설정
            Basket basket = new Basket();

            Long materialOrders = (Long) basketInfo[0];
            int number = (int) basketInfo[1];

            Material material = materialRepository.findByOrders(materialOrders);
            basket.setMaterial(material);
            basket.setOrders(materialOrders);
            basket.setNumber(number);

            // Board와 Basket 연결
            board.setBasket(basket);

            // User, Material, Basket, Board 저장
            userRepository.save(user);
            materialRepository.save(material);
            basketRepository.save(basket);
            boardRepository.save(board);
        }

        return BoardDto.toDto(board);
    }


    // 게시물 수정
    @Transactional
    public BoardDto update(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });

        board.setTitle(boardDto.getTitle());
        board.setContents(boardDto.getContents());

        return BoardDto.toDto(board);
    }


    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        // 매개변수 id를 기반으로, 게시글이 존재하는지 먼저 찾음
        // 게시글이 없으면 오류 처리
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });

        // 게시글이 있는 경우 삭제처리
        boardRepository.deleteById(id);

    }
}