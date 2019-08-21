package com.lishuang.community.community.controller;

import com.lishuang.community.community.dto.PaginationDTO;
import com.lishuang.community.community.model.User;
import com.lishuang.community.community.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InformationListController {
    @Autowired
    private PaginationService paginationService;

    @GetMapping("/information-list")
    public String informationList(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "5") Integer size
                                  ){

        //获取user
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登陆");
            return "redirect:/";
        }
        //List<Information> informationList = informationMapper.findAll();
        //model.addAttribute("information", informationList);
        //BeanUtils.copyProperties(information, informationDTO);将一个对象的值直接拷贝成另一个对象的值

        //实现分页
        PaginationDTO paginationDTO = paginationService.findAll(page,size);
        model.addAttribute("pagination",paginationDTO);
        return "information-list";
    }

}
