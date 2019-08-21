package com.lishuang.community.community.service;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.FormatPreservingEncrytionEmail;
import com.idealista.fpe.FormatPreservingEncrytionName;
import com.idealista.fpe.FormatPreservingEncrytionPhone;
import com.lishuang.community.community.dto.PaginationDTO;
import com.lishuang.community.community.mapper.InformationMapper;
import com.lishuang.community.community.model.Information;
import com.lishuang.community.community.provider.ConstantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PaginationService {
    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private ConstantProperties constantProperties;

    public PaginationDTO findAll(Integer page, Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        //查询记录总数
        Integer totalCount = informationMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size*(page-1);
        //查询到一页
        List<Information> informationList = informationMapper.findAll(offset,size);
/*
        byte[] akey = new byte[0];
        try {
            akey = FormatPreservingEncryption.generateKey(constantProperties.getKey());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //替换密文号码
        for(Information information:informationList){
            String name = information.getName();
            String phone = information.getPhone();
            //String email = information.getEmail();
            try {
                FormatPreservingEncrytionName fpe = new FormatPreservingEncrytionName();
                byte[] tweak = fpe.tweakOfEncrypt(name);

                FormatPreservingEncrytionPhone formatPreservingEncrytionPhone = new FormatPreservingEncrytionPhone();
                String plainPhone = formatPreservingEncrytionPhone.decryptPhone(phone, akey, tweak);
                information.setPhone(plainPhone);
                /*
                FormatPreservingEncrytionEmail formatPreservingEncrytionEmail = new FormatPreservingEncrytionEmail();
                String plainEmail = formatPreservingEncrytionEmail.decryptEmail(information.getEmail(),akey,tweak);
                information.setEmail(plainEmail);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }*/
        //System.out.println(informationList);
        paginationDTO.setInformationList(informationList);
        return paginationDTO;
    }

}
