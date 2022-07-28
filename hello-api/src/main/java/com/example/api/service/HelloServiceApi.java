package com.example.api.service;

import com.example.commons.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author cph
 * @date 2022/07/28
 */
public interface HelloServiceApi {

    @GetMapping("/hello11")
    String hello();

    @GetMapping("/hello22")
    String hello2Api(@RequestParam("name") String name);

    @PostMapping("/hello44")
    Map<String, Object> mapApi(@RequestParam Map<String, Object> map);

    @PostMapping("/hello55")
    User userApi(@RequestBody User user);

}
