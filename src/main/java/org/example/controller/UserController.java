package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

//        if (username !=null && username.length()>=5 && username.length()<=16
//        && password!=null && password.length()>=5 && password.length() <=16
//        ) {
            User u = userService.findByUserName(username);
            if(u==null) {
                userService.register(username, password);
                return Result.success();
            } else {
                return Result.error("user name is existed");
            }

//        } else {
//            return Result.error("invalid parameter");
//        }

    }
}











