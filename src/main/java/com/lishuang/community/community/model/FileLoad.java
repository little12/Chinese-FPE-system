package com.lishuang.community.community.model;

import lombok.Data;

@Data
public class FileLoad {
    private int id;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private String gmtCreate;
    private String gmtModified;


}
