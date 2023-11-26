package com.example.cms_webproject.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_order")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_order")
    private Material material;
    private int number;

    @OneToMany(mappedBy = "basket")
    List<Board> boards = new ArrayList<Board>();
}
