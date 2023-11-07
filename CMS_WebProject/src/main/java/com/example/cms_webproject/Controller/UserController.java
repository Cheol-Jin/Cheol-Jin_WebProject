package com.example.cms_webproject.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cms_webproject.Model.User;
import com.example.cms_webproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
//...
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    @ResponseBody
    public Map<String, Object> create(@RequestBody User user) {
        // 회원 가입 ver1
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());

        Map resultTable = new HashMap<>();

        if (!Pattern.matches( "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",user.getEmail())){
            resultTable.put("message","이메일 형식이 잘못된 형식입니다.");
            resultTable.put("status",401);
            return resultTable;
        }

        User findedUser = userRepository.findById(user.getId());

        if (findedUser != null){
            // 이메일 중복
            resultTable.put("message","중복된 아이디입니다.");
            resultTable.put("status",401);
            return resultTable;
        }

        userRepository.save(user);

        resultTable.put("message","회원가입 성공.");
        resultTable.put("status",201);

        return resultTable;
    }

    @GetMapping("/user/{order}")
    public Map<String,Object> read(@PathVariable Long order) {
        Map resultTable = new HashMap<>();
        Optional<User> userOptional = userRepository.findById(order);
        if (userOptional.isPresent()) {
            resultTable.put("userinfo",userOptional.get());
            resultTable.put("message","정보조회성공");
            resultTable.put("status","201");
        } else {
            resultTable.put("message","정보를 조회할 수 없습니다.");
            resultTable.put("status","401");//없어도 되지 않나...
        }
        return resultTable;
    }




    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map resultTable = new HashMap<>();
        User findedUser = userRepository.findById(user.getId());
        System.out.println(findedUser);

        if(findedUser == null) {
            resultTable.put("message","존재하지 않는 아이디입니다.");
            resultTable.put("status",401);
            return resultTable;
        }
        if(!findedUser.getPassword().equals(user.getPassword())) {
            resultTable.put("message","비밀번호가 일치하지 않습니다.");
            resultTable.put("status",401);
            return resultTable;
        }

        // accestoken -> 유저정보 요청 가능
        resultTable.put("message","로그인 성공.");
        resultTable.put("status",200);
        resultTable.put("User_id",findedUser.getOrder());

        return resultTable;
    }

}
