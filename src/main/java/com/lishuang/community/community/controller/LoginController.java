package com.lishuang.community.community.controller;

import com.lishuang.community.community.mapper.SignMapper;
import com.lishuang.community.community.model.Sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private SignMapper signMapper;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login")
    public  String signIn(@RequestParam("email") String email,
                          @RequestParam("password") String password,
                          HttpServletRequest request,
                          Model model){
        //空白登录
        if (email == null || password == null) {
            model.addAttribute("error","登录失败，请输入正确的用户名和密码");
            return "redirect:/";
        }

        List<Sign> users = signMapper.findAll();
        for(Sign user:users){
            String md5info = user.getEmail().toLowerCase() + user.getPassword().toLowerCase();
            String realPassword = DigestUtils.md5DigestAsHex(md5info.getBytes());
            if (password.equals(realPassword)) {
                return "redirect:/";
            }
        }
        model.addAttribute("error","登录失败，请输入正确的用户名和密码");
        return "login";
    }

    @GetMapping("/sign_up")
    public String Sign(){
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String signUp(@RequestParam("username") String username,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         HttpServletResponse response) {

        if (username == null || email == null || null == password) {
            return "sign_up";
        }
        Sign sign = new Sign();
        sign.setUsername(username);
        sign.setEmail(email);
        sign.setPassword(password);
        String token = UUID.randomUUID().toString();
        sign.setToken(token);
        response.addCookie(new Cookie("token", token));

        signMapper.create(sign);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }




}
