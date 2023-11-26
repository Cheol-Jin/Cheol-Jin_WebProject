package com.example.cms_webproject.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;

    private String name;
    private String brand;
    private String uses;
    private String matter;
    private Long price;
    private String image;
    @OneToMany(mappedBy = "material")
    private List<Basket> baskets;
}


