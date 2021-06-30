package com.weilaizhe.common.multipart.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author dameizi
 * @ClassName com.com.member.member.multipart.service
 * @Description File Service
 * @Date 2019/4/7 20:59
 * @Version 1.0
 */
public interface IFileService {

/**
     * @author: dameizi
     * @dateTime: 2019-04-02 13:32
     * @description: 上传图片文件到阿里云
     * @param: [bucketName, folder, file, fileName]
     * @return: java.lang.String
     */
    String uploadOSSPictureObject(String bucketName, String folder, MultipartFile file, String fileName) throws IOException;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 13:31
     * @description: 上传图片文件到阿里云，文件名默认为时间戳
     * @param: [bucketName, folder, file]
     * @return: java.lang.String
     */
    String uploadOSSPictureObject(String bucketName, String folder, MultipartFile file) throws IOException;

}
