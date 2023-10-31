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
    public Map<String, Object> create(@RequestParam String name ,@RequestParam String email,@RequestParam String pw) {
        // 회원 가입 ver1
        System.out.println(name);
        System.out.println(email);
        System.out.println(pw);

        Map resultTable = new HashMap<>();

        User createdUser = new User();
        createdUser.setName(name);
        createdUser.setEmail(email);
        createdUser.setPassword(pw);

        User findedUser = userRepository.findByEmail(createdUser.getEmail());

        if (!Pattern.matches( "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",createdUser.getEmail())){
            resultTable.put("message","이메일 형식이 잘못된 형식입니다.");
            resultTable.put("status",401);
            return resultTable;
        }


        if (findedUser != null){
            // 이메일 중복
            resultTable.put("message","중복된 이메일입니다.");
            resultTable.put("status",401);
            return resultTable;
        }

        userRepository.save(createdUser);

        resultTable.put("message","회원가입 성공.");
        resultTable.put("status",201);

        return resultTable;
    }

    @GetMapping("/user/{order}")
    public String read(@PathVariable Long order) {

        Optional<User> userOptional = userRepository.findById(order);
        userOptional.ifPresent(System.out::println);

        return "successfully executed";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map resultTable = new HashMap<>();
        User findedUser = userRepository.findByEmail(user.getEmail());
        System.out.println(findedUser);

        if(findedUser == null) {
            resultTable.put("message","존재하지 않는 이메일입니다.");
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
