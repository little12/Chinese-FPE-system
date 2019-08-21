package com.lishuang.community.community.controller;

import com.lishuang.community.community.mapper.UserMapper;
import com.lishuang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *  访问Index模板
 */
@Controller
public class indexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    /*已经放入拦截器中
    public String index(HttpServletRequest request){

        Cookie[] cookies=request.getCookies();
        if (cookies != null && cookies.length != 0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        return "index";
    }*/
}
