package com.example.provider.web;

import com.example.commons.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cph
 * @date 2022/07/24
 */
@Controller
public class RegisterController {
    @PostMapping("/register")
    public String register(User user) {
        return "redirect:http://provider/loginPage?username=" + user.getUsername();
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username) {
        return "loginPage：" + username;
    }
}
