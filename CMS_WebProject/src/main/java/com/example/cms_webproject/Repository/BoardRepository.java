package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Model.Basket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cms_webproject.Model.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"user", "basket", "basket.material"})
    Optional<Board> findById(Long id);

}

