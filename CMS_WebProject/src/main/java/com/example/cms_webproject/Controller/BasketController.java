package com.example.cms_webproject.Controller;

import com.example.cms_webproject.Model.Basket;
import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Repository.BasketRepository;
import com.example.cms_webproject.Repository.MaterialRepository;
import com.example.cms_webproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BasketController {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @GetMapping("/basket/{orders}")
    public Map<String,Object> getbasket(@PathVariable Long user_orders){
        Map<String,Object> resultTable = new HashMap<>();
        User user = new User();
        user.setOrders(user_orders);
        if(!basketRepository.findByUser(user).isEmpty()){
            List<Basket> data = basketRepository.findByUser(user);
            resultTable.put("data", data);
            resultTable.put("message","해당 orders인 유저의 장바구니를 가져왔습니다.");
            resultTable.put("status",201);
        }
        else{
            resultTable.put("message","해당 orders인 유저가 없습니다.");
            resultTable.put("status",401);
        }
        return resultTable;
    }
    //가져올때 보통 user정보랑 뭐시기랑 맞게 가져오지 않나...
    @PostMapping("/basket")
    public Map<String,Object> putbasket(@RequestParam Long user_orders,@RequestParam Long material_orders,@RequestParam int number){

        Map<String,Object> resultTable = new HashMap<>();
        Basket newbasket = new Basket();
        System.out.println(material_orders);
        System.out.println(user_orders);
        try {
            Material material = new Material();
            material.setOrders(material_orders);
            User user = new User();
            user.setOrders(user_orders);
            newbasket.setUser(user);
            newbasket.setMaterial(material);
            newbasket.setNumber(number);
            basketRepository.save(newbasket);
            resultTable.put("message", "해당 장바구니가 기록됌");
            resultTable.put("stats", 201);
        }
        catch (Exception exception){
            System.out.println(exception.getCause());
            resultTable.put("message","해당 user_orders나 material_orders를 가진 개체가 없음");
            resultTable.put("status",401);
        }

        return resultTable;

    }
    @DeleteMapping("/basket")
    public Map<String,Object> dropbasket(@RequestParam Long user_orders, @RequestParam Long material_orders){
        Map<String,Object> resultTable = new HashMap<>();
        User user = new User();
        user.setOrders(user_orders);
        Material material = new Material();
        material.setOrders(material_orders);
        if(basketRepository.findByUserAndMaterial(user, material)!=null){
            basketRepository.deleteByUserAndMaterial(user,material);
           resultTable.put("message","해당 user와 해당 material의 장바구니 삭제");
           resultTable.put("status",201);
        }
        else{
            resultTable.put("message","해당 user_orders와 material_orders에 해당하는 장바구니가 비어있습니다.");
            resultTable.put("status",401);
        }
        return resultTable;
    }

    @PutMapping("/basket")
    public Map<String,Object> numberbasket(@RequestParam Long user_orders,@RequestParam Long material_orders,@RequestParam int number){
        Map<String,Object> resultTable= new HashMap<>();
        User user = new User();
        user.setOrders(user_orders);
        Material material = new Material();
        material.setOrders(material_orders);
        if(basketRepository.findByUserAndMaterial(user,material)==null){
            resultTable.put("message","해당 user_orders나 material_orders가 없다.");
            resultTable.put("status",401);
        }
        else {
            Basket basket = basketRepository.findByUserAndMaterial(user, material);
            basket.setNumber(number);
            basketRepository.save(basket);
            resultTable.put("message","해당 user와 material의 장바구니 목록 삭제");
            resultTable.put("status",201);
        }
        return resultTable;
    }
}
