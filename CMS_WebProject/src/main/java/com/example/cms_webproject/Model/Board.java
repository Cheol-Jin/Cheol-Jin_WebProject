package com.example.cms_webproject.Model;

import com.example.cms_webproject.Service.BoardService;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_order")
    private User user;

    @Column(nullable = false)
    private String subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "basket_order")
    private Basket basket;

}