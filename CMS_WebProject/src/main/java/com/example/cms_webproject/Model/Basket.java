package com.example.cms_webproject.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_order", nullable = true)
    private User user;

    //Basket에 user를 설정하는 메서드
    public void setUser(User user) {
        this.user = user;
        if (!user.getBaskets().contains(this)) {
            user.getBaskets().add(this);
        }
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "material_order")
    @MapsId("orders")
    private Material material;

    private int number;

    @OneToMany(mappedBy = "basket")
    List<Board> boards = new ArrayList<Board>();
}
