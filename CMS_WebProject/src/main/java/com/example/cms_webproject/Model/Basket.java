package com.example.cms_webproject.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;
    @ManyToOne
    @JoinColumn(name="user_order")
    private User user;
    @ManyToOne
    @JoinColumn(name="material_order")
    private Material material;
    private int number;
}
