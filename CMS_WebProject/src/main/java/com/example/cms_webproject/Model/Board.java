package com.example.cms_webproject.Model;

import com.example.cms_webproject.Service.BoardService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "orders")
    private Long ordersBoard;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String status; //역경매 진행 현황

    @CreationTimestamp
    @Column(nullable = false, name = "date")
    private LocalDate createdAt;

    @Column(nullable = false, name = "count")
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_order")
    private User user;

    @Column(nullable = false)
    private String subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "basket_order")
    private Basket basket;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Board{ordersBoard=" + ordersBoard + "}";
    }


}