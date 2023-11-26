package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Dto.BasketInfoDto;
import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket,Long> {
    Basket findByUserAndMaterial(User user, Material material);
    @Transactional
    void deleteByUserAndMaterial(User user, Material material);
    List<Basket> findByUser(User user);

    List<Basket> findAllByUserOrders(Long userOrder);

    @Query("SELECT b.material.orders, b.number " +
            "FROM Basket b " +
            "WHERE b.user.orders = :userOrder")
    List<Object[]> getBasketInfoByUserOrder(@Param("userOrder") Long userOrder);

    //특정 유저 번호에 해당하는 모든 장바구니를 불러온다 (material_order, number)

}
