package com.spongebob.socalshopping.controller;

import com.spongebob.socalshopping.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    Map<String, User> users = new HashMap<>();
    @Resource(name="lisi")
    User nobodyUser;
    @PostMapping("/users")
    public String createUser(@RequestParam() String name,
                             @RequestParam() String email,
                             Map<String, Object> resultMap){
        User user = new User(name, email);
        users.put(name, user);//email
        resultMap.put("user", user);
        return "user_detail";

    }

    @GetMapping("/users")
    public String createUser(@RequestParam() String name,
                             Map<String, Object> resultMap){
        //如果查不到，默认nobody
        User user = users.getOrDefault(name, nobodyUser);
        resultMap.put("user", user);
        return "user_detail";

    }
}
