package com.weilaizhe.common.multipart.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.multipart.service.IFileService;
import com.weilaizhe.common.util.CommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: dameizi
 * @description: File Service
 * @dateTime 2019-04-01 23:18
 * @className com.weilaizhe.common.multipart.service.impl.FileServiceImpl
 */
@Service
public class FileServiceImpl implements IFileService {

    private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

    /** endpoint是访问OSS的域名 */
    @Value("${oss.client.endpoint}")
    private String endpoint;
    /** accessKeyId和accessKeySecret是OSS的访问密钥 */
    @Value("${oss.client.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.client.accessKeySecret}")
    private String accessKeySecret;
    /** 图片大小最大值 */
    @Value("${file.picture.max.size}")
    private int pictureMaxSize;
    /** 图片文件后缀名格式 */
    @Value("${file.picture.suffix.format}")
    private String pictureSuffixFormat;
    /** Import File Path */
    @Value("${file.import.path}")
    public  String FILE_IMPORT_PATH;
    /** Upload File Path */
    @Value("${file.upload.path}")
    public  String FILE_UPLOAD_PATH;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 13:32
     * @description: 上传图片文件到阿里云
     * @param: [bucketName, folder, file, fileName]
     * @return: java.lang.String
     */
    @Override
    public String uploadOSSPictureObject(String bucketName, String folder, MultipartFile file, String fileName) throws IOException {
        OSSClient ossClient = null;
        InputStream inputStream = null;
        try {
            // 检查图片文件和文件名
            this.checkPictureFile(file);
            // 读取文件流
            inputStream = file.getInputStream();
            // 实例化OSSClient
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 检查并创建 Bucket 和 Folder
            this.checkBucketAndFolder(ossClient, bucketName, folder);
            // 获取文件的后缀名
            String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(CommonConst.DOT));
            // 如果传过来的文件名为空，则根据时间戳生成文件名
            if(StringUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis()) + suffixName;
            }
            //上传文件 (上传文件流的形式)
            ossClient.putObject(bucketName, folder + CommonConst.SPRIT + fileName, inputStream);
            StringBuffer url = new StringBuffer(CommonConst.URL_HTTPS);
            url.append(bucketName).append(CommonConst.DOT)
                    .append(this.endpoint.substring(this.endpoint.lastIndexOf(CommonConst.OSS))).append(CommonConst.SPRIT)
                    .append(folder).append(CommonConst.SPRIT).append(fileName);
            return url.toString();
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 13:31
     * @description: 上传图片文件到阿里云，文件名默认为时间戳
     * @param: [bucketName, folder, file]
     * @return: java.lang.String
     */
    @Override
    public String uploadOSSPictureObject(String bucketName, String folder, MultipartFile file) throws IOException {
        return this.uploadOSSPictureObject(bucketName, folder, file, null);
    }

    /**
     * @Author dameizi
     * @Description 检查上传的图片文件
     * @DateTime 2018/6/14 20:41
     * @param file 上传的图片
     * @return InputStream 返回文件流
     */
    private void checkPictureFile(MultipartFile file){
        if (file == null) {
            throw new DescribeException(CommonConst.UPLOAD_FILE_NOT_EMPTY, 0);
        }
        // 文件大小校验
        if(this.pictureMaxSize < file.getSize( )) {
            throw new DescribeException(CommonConst.IMAGE_MAX + this.pictureMaxSize + "字节", 0);
        }
        //文件名
        String fName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fName.substring(fName.lastIndexOf(CommonConst.DOT)) + CommonConst.COMMA;
        if(this.pictureSuffixFormat.indexOf(suffixName) < 0) {
            throw new DescribeException(CommonConst.IMAGE_UPLOAD_SUFFIX + this.pictureSuffixFormat + "的文件", 0);
        }
    }

    /**
     * @Author dameizi
     * @Description 检查并创建bucket 和 文件夹
     * @DateTime 2018/6/14 20:51
     * @param ossClient oss客户端
     * @param bucketName 云服务器存储空间
     * @param folder 文件夹名称
     * @return void
     */
    private void checkBucketAndFolder(OSSClient ossClient, String bucketName, String folder) {
        // 非空校验
        CommonUtil.paramEmptyVerify(bucketName, CommonConst.BUCKET_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(folder, CommonConst.FOLDER_NOT_EMPTY);
        // 判断Bucket是否存在，如果不存在，则创建新的Bucket
        if (!ossClient.doesBucketExist(bucketName)) {
            logger.info(CommonConst.MKDIR_BUCKET + bucketName);
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            // 设置Bucket权限为公共读，默认是私有读写。
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            // 设置Bucket存储类型为低频访问类型，默认是标准类型。
            // createBucketRequest.setStorageClass(StorageClass.IA);
            ossClient.createBucket(createBucketRequest);
        }
        //判断文件夹是否存在，不存在则创建
        if(!ossClient.doesObjectExist(bucketName, folder)){
            logger.info(CommonConst.MKDIR_FOLDER + folder);
            ossClient.putObject(bucketName, folder, new ByteArrayInputStream(new byte[0]));
        }
    }

}
