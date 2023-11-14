package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket,Long> {
    Basket findByUserAndMaterial(User user, Material material);
    void deleteByUserAndMaterial(User user, Material material);
    List<Basket> findByUser(User user);

}
