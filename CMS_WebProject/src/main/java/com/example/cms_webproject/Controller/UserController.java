package com.example.cms_webproject.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    @ResponseBody
    public String create(@RequestParam String name ,@RequestParam String email,@RequestParam String pw) {
        // 회원 가입 ver1
        System.out.println(name);
        System.out.println(email);
        System.out.println(pw);

        User createdUser = new User();
        createdUser.setName(name);
        createdUser.setEmail(email);

        // 해쉬 함수를 정요한 결과를 db에 저장 한다.
        createdUser.setPassword(pw);

        User test = userRepository.save(createdUser);
        System.out.println(test);

        return "회원가입 성공";
    }

    @GetMapping("/user/{order}")
    public String read(@PathVariable Long order) {

        Optional<User> userOptional = userRepository.findById(order);
        userOptional.ifPresent(System.out::println);

        return "successfully executed";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // login ver.1


        User findedUser = userRepository.findByEmail(user.getEmail());
        System.out.println(findedUser);
        if(findedUser == null) return null;
        if(!findedUser.getPassword().equals(user.getPassword())) return null;

        // accestoken -> 유저정보 요청 가능

        return "Login 성공 &order="+findedUser.getOrder();
    }

}
