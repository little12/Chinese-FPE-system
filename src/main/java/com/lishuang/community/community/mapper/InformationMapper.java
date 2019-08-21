package com.lishuang.community.community.mapper;

import com.lishuang.community.community.model.Information;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InformationMapper {
    @Insert("insert into information(user, name, new_name,sex, phone, email, gmt_created, gmt_modified) values (#{user},#{name},#{newName}, #{sex}, #{phone}, #{email}, #{gmtCreated}, #{gmtModified})")
    void create(Information information);

    //@Select("select * from information")
    //List<Information> findAll();

    @Select("select * from information limit #{offset}, #{size}")
    List<Information> findAll(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from information")
    Integer count();


}