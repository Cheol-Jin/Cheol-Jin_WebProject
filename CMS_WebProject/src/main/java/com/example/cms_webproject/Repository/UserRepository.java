package com.example.cms_webproject.Repository;

import com.example.cms_webproject.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String id);
    Optional<User> findByOrders(Long orders);
@Transactional
    void deleteByOrders(Long orders);

}