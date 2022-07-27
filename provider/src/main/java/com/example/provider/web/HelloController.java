package com.example.provider.web;

import com.example.commons.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author cph
 * @date 2022/07/17
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/hello")
    public String hello() {
        return "Hello Cloud：" + port;
    }

    @GetMapping("/hello2")
    public String hello2(String name) {
        return "Hello Cloud：" + port + "：" + name;
    }

    @GetMapping("/hello3")
    public String hello3Post(String name) {
        return "Hello Cloud：" + port + "：" + name;
    }

    /* post 演示 kv 接收对象，JSON 接收对象 */
    @PostMapping("hello4")
    public Map hello4(/*User user*/@RequestParam Map map) {
        return map;
    }

    @PostMapping("hello5")
    public User hello5(@RequestBody User user) {
        return user;
    }

    @DeleteMapping("hello6")
    public void hello6(Integer id) {
        System.out.println("H6" + id);
    }

    @DeleteMapping("hello7/{id}")
    public void hello7(@PathVariable Integer id) {
        System.out.println("H7" + id);
    }
}
