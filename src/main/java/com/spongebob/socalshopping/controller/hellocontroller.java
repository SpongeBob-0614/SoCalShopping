package com.spongebob.socalshopping.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Controller
@ResponseBody
public class hellocontroller {




    @Resource
    DependencyA dependencyA;

    public hellocontroller(DependencyA dependencyA) {
        this.dependencyA = dependencyA;
    }

    @PostMapping("/hello")
    public String helloget(){
        return dependencyA.send("Post Method");
    }
    @GetMapping("/hello")
    public String hello() {
        return dependencyA.send("Get Method");
    }

    @GetMapping("/echo/{text}")
    public String echo(@PathVariable("text") String abc) {
        return "You just input: " + abc;
    }

}

