package com.spongebob.socalshopping.configuration;

import com.spongebob.socalshopping.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean(name="nobody")
    public User getNobody(){
        return new User("nobody","Nobody@gamil.com");
    }

    @Bean(name="lisi")
    public User getLisi(){
        return new User("lisi","lisi@gmail.com");
    }
}
