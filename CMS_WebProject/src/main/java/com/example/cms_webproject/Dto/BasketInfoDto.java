package com.example.cms_webproject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketInfoDto {
    private Long materialOrders;
    private int Number;
    private Long userId;

    public BasketInfoDto(Long materialOrders, int Number) {
        this.materialOrders = materialOrders;
        this.Number = Number;
    }


}