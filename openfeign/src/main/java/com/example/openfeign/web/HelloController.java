package com.example.openfeign.web;

import com.example.commons.User;
import com.example.openfeign.service.HelloProviderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author cph
 * @date 2022/07/26
 */
@RestController
public class HelloController {

    private HelloProviderApi helloProviderApi;

    @Autowired
    public HelloController(HelloProviderApi helloProviderApi) {
        this.helloProviderApi = helloProviderApi;
    }

    @GetMapping("/hello")
    public String hello() {
        return helloProviderApi.helloApi();
    }

    @GetMapping("/hello2")
    public String hello2Api(@RequestParam("name") String name) {
        return helloProviderApi.hello2Api(name);
    }

    @PostMapping("/hello4")
    public Map<String, Object> mapApi(@RequestParam Map<String, Object> map) {
        return helloProviderApi.mapApi(map);
    }

    @PostMapping("/hello5")
    public User userApi(@RequestBody User user) {
        return helloProviderApi.userApi(user);
    }
}
