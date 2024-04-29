package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material,Long> {
    @Query("SELECT m FROM Material m WHERE (m.brand IN :brand) AND  (m.matter IN :matter) AND (m.uses IN :uses)")
    List<Material> findFilteredMaterials(@Param("brand") List<String> brand, @Param("matter") List<String> matter, @Param("uses") List<String> uses, Pageable pageable);

    @Query("SELECT DISTINCT m.brand FROM Material m")
    List<String> findAllBrands();
    @Query("SELECT DISTINCT m.uses FROM Material m")
    List<String> findAllUses();

    @Query("SELECT DISTINCT m.matter FROM Material m")
    List<String> findAllMatters();
    Material findByName(String name);
    List<Material> findByNameContaining(String name);
    Material findByOrders(Long orders);

    @Transactional
    void deleteByOrders(Long orders);
}
