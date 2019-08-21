package com.lishuang.community.community.controller;

import com.lishuang.community.community.mapper.FileLoadMapper;
import com.lishuang.community.community.model.FileLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class FileController {
    @Autowired
    private FileLoadMapper fileLoadMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(InformationListController.class);

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    //@ResponseBody
    public String upload(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "upload";
        }
        //获取原始名字
        String fileName=file.getOriginalFilename();
        // 文件保存路径
        String filePath = "d:/upload/";
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //文件重命名
        fileName = UUID.randomUUID().toString().replace("-", "");
                //+ fileName.substring(fileName.lastIndexOf("."));

        File targetFile = new File(filePath+fileName);
        try {
            file.transferTo(targetFile);
            //LOGGER.info("upload successful");
            //return "true";
            //model.addAttibute("hint","上传成功");
            FileLoad fileLoad = new FileLoad();
            fileLoad.setFileName(fileName);
            fileLoad.setFileDownloadUri(filePath);
            fileLoad.setFileType(suffix);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fileLoad.setGmtCreate(dateformat.format(System.currentTimeMillis()));
            fileLoad.setGmtModified(fileLoad.getGmtCreate());
            fileLoadMapper.create(fileLoad);
            return "upload";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "upload";
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response) throws Exception {
        // 文件地址，真实环境是存放在数据库中的
        File file = new File("D:\\upload\\a.txt");
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + "a.txt");
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }

}
