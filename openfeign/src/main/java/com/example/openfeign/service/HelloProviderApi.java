package com.example.openfeign.service;

import com.example.api.service.HelloServiceApi;
import com.example.commons.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author cph
 * @date 2022/07/26
 */
@FeignClient("provider")
public interface HelloProviderApi extends HelloServiceApi {

    /*@GetMapping("/hello")
    String helloApi();

    @GetMapping("/hello2")
    String hello2Api(@RequestParam("name") String name);

    @PostMapping("/hello4")
    Map<String, Object> mapApi(@RequestParam Map<String, Object> map);

    @PostMapping("/hello5")
    User userApi(@RequestBody User user);*/

}
