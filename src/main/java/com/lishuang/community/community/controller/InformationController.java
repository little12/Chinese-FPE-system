package com.lishuang.community.community.controller;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.FormatPreservingEncrytionEmail;
import com.idealista.fpe.FormatPreservingEncrytionName;
import com.idealista.fpe.FormatPreservingEncrytionPhone;
import com.lishuang.community.community.mapper.InformationMapper;
import com.lishuang.community.community.model.Information;
import com.lishuang.community.community.model.User;
import com.lishuang.community.community.provider.ConstantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;

@Controller
public class InformationController {
   @Autowired
   private InformationMapper informationMapper;

   @Autowired
   private ConstantProperties constantProperties;

    @GetMapping("/information")
    public String information(){
        return "information";
    }

    @PostMapping("/information")
    public String doInformation(
            @RequestParam("name") String name,
            @RequestParam("sex") String sex,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            HttpServletRequest request,
            Model model){

        //获取user
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登陆");
            return "redirect:/";
        }

        if(name.length()>4){
            model.addAttribute("error","姓名格式错误");
            return "information";
        }
        Information information = new Information();
        information.setUser(user.getName());
        information.setName(name);
        byte[] akey= new byte[0];
        try {
            akey = FormatPreservingEncryption.generateKey(constantProperties.getKey());
            FormatPreservingEncrytionName fpe = new FormatPreservingEncrytionName();
            byte[] tweak= fpe.tweakOfEncrypt(name);
            String cipher = fpe.encryptName(name, akey,tweak);
            information.setNewName(cipher);

            FormatPreservingEncrytionPhone formatPreservingEncrytionPhone = new FormatPreservingEncrytionPhone();
            String cipherPhone = formatPreservingEncrytionPhone.encryptPhone(phone,akey,tweak);
            information.setPhone(cipherPhone);

            FormatPreservingEncrytionEmail formatPreservingEncrytionEmail = new FormatPreservingEncrytionEmail();
            String cipherEmail = formatPreservingEncrytionEmail.encryptEmail(email,akey,tweak);
            information.setEmail(cipherEmail);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        information.setSex(sex);

        //information.setPhone(phone);
        //information.setEmail(email);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        information.setGmtCreated(dateformat.format(System.currentTimeMillis()));
        information.setGmtModified(information.getGmtCreated());
        informationMapper.create(information);
        //return "redirect:/";
        return "information";
    }
}
