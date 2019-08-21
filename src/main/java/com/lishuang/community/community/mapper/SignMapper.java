package com.lishuang.community.community.mapper;

import com.lishuang.community.community.model.Sign;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SignMapper {
    @Insert("insert into admin(username, email, password,token) values (#{username},#{email},#{password}, #{token})")
    void create(Sign sign);

    @Select("select * from admin where token = #{token}")
    Sign findByToken(@Param("token") String token);

    @Select("select * from admin")
    List<Sign> findAll();
}
