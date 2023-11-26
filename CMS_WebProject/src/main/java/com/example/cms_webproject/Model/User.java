package com.example.cms_webproject.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
    //약관
    private boolean is14over;
    private boolean service;
    private boolean privacyinfo;
    private boolean marketing;
    public User(Long orders){
        this.orders = orders;
    }

    public User() {
    }
}
