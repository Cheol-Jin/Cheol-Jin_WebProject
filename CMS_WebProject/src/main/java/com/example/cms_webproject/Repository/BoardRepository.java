package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cms_webproject.Model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}

