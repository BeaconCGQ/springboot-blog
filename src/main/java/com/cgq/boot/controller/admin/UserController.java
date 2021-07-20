package com.cgq.boot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgq.boot.pojo.User;
import com.cgq.boot.service.UserService;
import com.cgq.boot.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
   private UserService userService;

    @GetMapping
    public String loginIndex(){

        return "admin/login";
    }

    @PostMapping("/login")
    public String login(String username,String password, HttpSession session, RedirectAttributes attributes){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password", MD5Utils.code(password));
        User user = userService.getOne(queryWrapper);

        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
           return "admin/index";
       }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
           return "redirect:/admin";
       }

    }
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
