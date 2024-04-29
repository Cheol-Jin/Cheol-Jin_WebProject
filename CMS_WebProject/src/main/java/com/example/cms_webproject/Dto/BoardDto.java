package com.example.cms_webproject.Dto;

import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cms_webproject.Model.Board;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class BoardDto {
    private Long ordersBoard;
    private String title;
    private String userId;
    private String contents;
    private String status;
    private String subject;
    private int count;
    private Long orders;
    private LocalDate createdAt;

    private Long materialOrders;
    private String materialName;
    private String materialBrand;
    private String materialUses;
    private String materialMatter;
    private int materialPrice;
    private String materialImage;

    private int numberOfMaterial;

    private List<CommentDto> comments;

    public BoardDto() {
    }

    public BoardDto(Long ordersBoard, String title, String userId, String contents, String status, String subject, int count, Long orders, LocalDate createdAt, Long materialOrders, String materialName, String materialBrand, String materialUses, String materialMatter, int materialPrice, String materialImage, int numberOfMaterial, List<CommentDto> comments) {
        this.ordersBoard = ordersBoard;
        this.title = title;
        this.userId = userId;
        this.contents = contents;
        this.status = status;
        this.subject = subject;
        this.count = count;
        this.orders = orders;
        this.createdAt = createdAt;
        this.materialOrders = materialOrders;
        this.materialName = materialName;
        this.materialBrand = materialBrand;
        this.materialUses = materialUses;
        this.materialMatter = materialMatter;
        this.materialPrice = materialPrice;
        this.materialImage = materialImage;
        this.numberOfMaterial = numberOfMaterial;
        this.comments = comments;
    }

    // Getters and Setters for all fields

    public static BoardDto toDto(Board board) {
        Material material = board.getBasket().getMaterial();
        Long materialOrders = material.getOrders();
        BoardDto boardDto = new BoardDto();

        // 중복된 Material 정보를 체크하고 추가합니다.
        boardDto.addMaterialOrder(materialOrders);

        // 중복된 Material이 아닌 경우에만 추가
        Basket basket = board.getBasket();

        boardDto.setOrdersBoard(board.getOrdersBoard());
        boardDto.setTitle(board.getTitle());
        boardDto.setUserId(board.getUser().getId());
        boardDto.setContents(board.getContents());
        boardDto.setSubject(board.getSubject());
        boardDto.setStatus(board.getStatus());
        boardDto.setCount(board.getCount());
        boardDto.setCreatedAt(board.getCreatedAt());
        boardDto.setOrders(board.getUser().getOrders());
        boardDto.setMaterialOrders(material.getOrders());
        boardDto.setMaterialName(material.getName());
        boardDto.setMaterialBrand(material.getBrand());
        boardDto.setMaterialUses(material.getUses());
        boardDto.setMaterialMatter(material.getMatter());
        boardDto.setMaterialPrice(material.getPrice());
        boardDto.setMaterialImage(material.getImage());
        boardDto.setNumberOfMaterial(basket.getNumber());

        return boardDto;
    }

    private void addMaterialOrder(Long materialOrders) {
    }

    public static BoardDto toDtoWithComments(Board board, List<CommentDto> comments) {
        Material material = board.getBasket().getMaterial();
        Basket basket = board.getBasket();

        return new BoardDto(
                board.getOrdersBoard(),
                board.getTitle(),
                board.getUser().getId(),
                board.getContents(),
                board.getStatus(),
                board.getSubject(),
                board.getCount(),
                board.getUser().getOrders(),
                board.getCreatedAt(),
                material.getOrders(),
                material.getName(),
                material.getBrand(),
                material.getUses(),
                material.getMatter(),
                material.getPrice(),
                material.getImage(),
                basket.getNumber(),
                comments
        );
    }
}