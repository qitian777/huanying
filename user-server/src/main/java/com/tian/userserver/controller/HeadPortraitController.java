package com.tian.userserver.controller;

import com.tian.serverapi.util.RespBean;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.config.exception.GlobalException;
import com.tian.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;

/**
 * @Description: 头像上传控制器
 * @Author QiGuang
 * @Date 2022/6/17
 * @Version 1.0
 */
@RestController
@Api(tags = "头像上传")
public class HeadPortraitController {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "头像上传")
    @PostMapping(value = "/upload")
    public RespBean logUpload(@RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        if (file == null || file.isEmpty()) {
            return RespBean.error(RespBeanEnum.FILE_UPLOAD_ERROR);
        }
        // 获取文件原名
        String originalFilename = file.getOriginalFilename();
        // 获取源文件前缀
        assert originalFilename != null;
        String fileNamePrefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        // 获取源文件后缀
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 用用户名当文件名
        String name = principal.getName();
        // 得到上传后新文件的文件名
        String newFileName = name + fileNameSuffix;
        // 创建一个新的File对象用于存放上传的文件
        File fileNew = new File(path + File.separator + newFileName);

        try {
            file.transferTo(fileNew);
            if (userService.updatePicture(newFileName,name)>0) return RespBean.success(RespBeanEnum.FILE_UPLOAD_SUCCESS);
        } catch (Exception e) {
            throw new GlobalException(RespBeanEnum.FILE_UPLOAD_ERROR);
        }
        return RespBean.error(RespBeanEnum.FILE_UPLOAD_ERROR);
    }
}
