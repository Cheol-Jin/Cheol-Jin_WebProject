package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cms_webproject.Model.Board;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"user", "basket", "basket.material", "comments"})
    Optional<Board> findById(Long orders);

    List<Basket> findAllByUserOrders(Long userOrder);
}

