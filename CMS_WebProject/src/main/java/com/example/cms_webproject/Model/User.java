package com.example.cms_webproject.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;

    private String id;

    private String password;

    private String email;
    //약관
    private boolean is14over;
    private boolean service;
    private boolean privacyinfo;
    private boolean marketing;
}
