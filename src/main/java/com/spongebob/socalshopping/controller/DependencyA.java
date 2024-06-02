package com.spongebob.socalshopping.controller;

import org.springframework.stereotype.Service;

@Service
public class DependencyA {
    public static String user = "Spongebob";
    public String send(String body){
        return body;
    }
}
