package com.lishuang.community.community.mapper;

import com.lishuang.community.community.model.FileLoad;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileLoadMapper {
    @Insert("insert into tb_file(file_name, file_download_uri, file_type, gmt_create, gmt_modified) values (#{fileName}, #{fileDownloadUri}, #{fileType}, #{gmtCreate}, #{gmtModified})")
    void create(FileLoad fileLoad);

    @Select("select * from tb_file where file_name = #{fileName}")
    FileLoad findByFileName(@Param("fileName") String fileName);
}
