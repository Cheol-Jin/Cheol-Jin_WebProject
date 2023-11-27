package com.example.cms_webproject.Dto;

import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cms_webproject.Model.Board;

import java.util.*;

@Data
public class BoardDto {
    private Long ordersBoard;
    private String title;
    private String contents;
    private String subject;
    private Long orders;

    private Long materialOrders;
    private String materialName;
    private String materialBrand;
    private String materialUses;
    private String materialMatter;
    private int materialPrice;
    private String materialImage;

    private int numberOfMaterial;

    // 중복된 Material 정보 체크용 Set
    private static Set<Long> uniqueMaterialOrders = new HashSet<>();

    // 중복된 Material 정보를 체크하고 추가하는 메서드
    public boolean addMaterialOrder(Long materialOrders) {
        return uniqueMaterialOrders.add(materialOrders);
    }

    public BoardDto() {
        // 기본 생성자에서 중복 데이터를 방지하기 위한 Set을 초기화
        uniqueMaterialOrders = new HashSet<>();
    }

    public BoardDto(
            Long ordersBoard,
            String title,
            String contents,
            String subject,
            Long orders,
            Long materialOrders,
            String materialName,
            String materialBrand,
            String materialUses,
            String materialMatter,
            int materialPrice,
            String materialImage,
            int numberOfMaterial
    ) {
        this.ordersBoard = ordersBoard;
        this.title = title;
        this.contents = contents;
        this.subject = subject;
        this.orders = orders;
        this.materialOrders = materialOrders;
        this.materialName = materialName;
        this.materialBrand = materialBrand;
        this.materialUses = materialUses;
        this.materialMatter = materialMatter;
        this.materialPrice = materialPrice;
        this.materialImage = materialImage;
        this.numberOfMaterial = numberOfMaterial;
    }

    public static BoardDto toDto(Board board) {
        Material material = board.getBasket().getMaterial();
        Long materialOrders = material.getOrders();
        BoardDto boardDto = new BoardDto();

        if (boardDto.addMaterialOrder(materialOrders)) {
            // 중복된 Material이 아닌 경우에만 추가
            Basket basket = board.getBasket();

            boardDto = new BoardDto(
                    board.getOrdersBoard(),
                    board.getTitle(),
                    board.getContents(),
                    board.getSubject(),
                    board.getUser().getOrders(),
                    material.getOrders(),
                    material.getName(),
                    material.getBrand(),
                    material.getUses(),
                    material.getMatter(),
                    material.getPrice(),
                    material.getImage(),
                    basket.getNumber()
            );
        }

        return boardDto;
    }
}