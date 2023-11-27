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

import java.util.*;

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
    public List<BoardDto> getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board Id를 찾을 수 없습니다."));

        List<BoardDto> boardDtoList = new ArrayList<>();

        // 중복 체크용 Set
        Set<Long> uniqueMaterialOrders = new HashSet<>();

        // 각 Material 정보에 대한 BoardDto 생성
        for (Board currentBoard : board.getBasket().getBoards()) {
            Material material = currentBoard.getBasket().getMaterial();
            Long materialOrders = material.getOrders();

            // 중복된 materialOrders가 없다면 BoardDto 추가
            if (uniqueMaterialOrders.add(materialOrders)) {
                BoardDto boardDto = new BoardDto(
                        currentBoard.getOrdersBoard(),
                        currentBoard.getTitle(),
                        currentBoard.getContents(),
                        currentBoard.getSubject(),
                        currentBoard.getUser().getOrders(),
                        material.getOrders(),
                        material.getName(),
                        material.getBrand(),
                        material.getUses(),
                        material.getMatter(),
                        material.getPrice(),
                        material.getImage(),
                        currentBoard.getBasket().getNumber()
                );

                boardDtoList.add(boardDto);
            }
        }

        // 중복 체크용 Set 초기화
        uniqueMaterialOrders.clear();

        return boardDtoList;
    }

    // 게시물 작성
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BoardDto> write(BoardDto boardDto) {
        User user = userRepository.findByOrders(boardDto.getOrders())
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        // 게시글 생성
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContents(boardDto.getContents());
        board.setSubject(boardDto.getSubject());

        // 중복 체크용 Set
        Set<Long> uniqueMaterialOrders = new HashSet<>();

        // 각 Material 정보에 대해 Basket 생성 및 저장
        List<BoardDto> result = new ArrayList<>();
        for (Object[] basketInfo : basketRepository.getBasketInfoByUserOrder(boardDto.getOrders())) {
            Long materialOrders = (Long) basketInfo[0];

            // 중복된 materialOrders가 없다면 데이터 추가
            if (uniqueMaterialOrders.add(materialOrders)) {
                Basket basket = new Basket();
                basket.setUser(user);

                // Material 정보 설정
                Material material = materialRepository.findByOrders(materialOrders);
                basket.setMaterial(material);
                basket.setOrders(materialOrders);
                basket.setNumber((int) basketInfo[1]);

                // Board와 Basket 연결
                board.setBasket(basket);
                board.setUser(user);

                // User와 Material을 먼저 저장
                userRepository.save(user);
                materialRepository.save(material);

                // Basket, Board 저장
                basketRepository.save(basket);
                boardRepository.save(board);

                // 결과 리스트에 BoardDto 추가
                result.add(BoardDto.toDto(board));
            }
        }

        // 중복 체크용 Set 초기화
        uniqueMaterialOrders.clear();

        return result;
    }


    // 게시물 수정
    @Transactional
    public BoardDto update(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });

        board.setTitle(boardDto.getTitle());
        board.setContents(boardDto.getContents());
        board.setSubject(boardDto.getSubject());

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