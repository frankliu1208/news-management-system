package org.example.controller;


import jakarta.validation.constraints.Pattern;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return Result.error("username is wrong");
        }

        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.error("password is wrong");
    }

    @GetMapping("/userinfo")
    public Result<User> userInfo(   /* @RequestHeader(name = "Authorization") String token  */  ) {

//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUserName(username);
        return Result.success(user);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    //  get the data from the query string
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    @PatchMapping("/updatePwd")
    public Result updatePwd (@RequestBody Map<String, String> params) {
         String oldPwd = params.get("old_pwd");
         String newPwd = params.get("new_pwd");
         String rePwd = params.get("re_pwd");  // re-confirm password

         if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
             return Result.error("lack necessary parameter");
         }

         Map<String, Object> map = ThreadLocalUtil.get();
         String username = (String) map.get("username");
         User loginUser = userService.findByUserName(username);
         if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
             return Result.error("original psd is wrong");
         }

         if (! rePwd.equals(newPwd)) {
             return Result.error("not aligned for the two new psd")
         }

         userService.updatePwd(newPwd);
         return Result.success();


    }


}
































