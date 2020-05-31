package kayleh.wizard.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import kayleh.wizard.community.exception.CustomizeErrorCode;
import kayleh.wizard.community.exception.CustomizeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: Wizard
 * @Date: 2020/5/31 14:54
 */
@Service
public class AliCloudProvider implements InitializingBean {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private  String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    @Value("${alicloud.accessKeyId}")
    private  String accessKeyId;
    @Value("${alicloud.accessKeySecret}")
    private  String accessKeySecret;
    @Value("${alicloud.bucketName}")
    private  String bucketName;

//    String objectName = "<yourObjectName>";

    public String upload(InputStream fileStream, String mimeType, String fileName) {
        String generatedFileName = null;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
//            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
//            ObjectConfig config = new ObjectConfig(region, suffix);
//            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
//                    .putObject(fileStream, mimeType)
//                    .nameAs(generatedFileName)
//                    .toBucket(bucketName)
//                    .setOnProgressListener((bytesWritten, contentLength) -> {
//                    })
//                    .execute();

            // 上传文件流。

            ossClient.putObject(bucketName, generatedFileName, fileStream);


            ossClient.shutdown();

            return generatedFileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
//            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


//    // 创建OSSClient实例。
//    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//    // 创建PutObjectRequest对象。
//    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, accessKeyId, new File("<yourLocalFile>"));
//
//    // 上传文件流。
//    InputStream inputStream = new FileInputStream("<yourlocalFile>");
//    ossClient.putObject("<yourBucketName>", "<yourObjectName>", inputStream);
//
//    // 关闭OSSClient。
//    ossClient.shutdown();
//
//    // 定义常量，为了能够使用
//    public static String ENDPOINT;
//    public static String KEYID;
//    public static String KEYSECRET;
//    public static String BUCKETNAME;
//
//    /**
//     * 服务器启动的时候，ConstantPropertiesUtil初始化，调用里面的afterPropertiesSet方法，读取配置文件的内容
//     *
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        ENDPOINT = endpoint;
//        KEYID = accessKeyId;
//        KEYSECRET = accessKeySecret;
//        BUCKETNAME = bucketName;
    }


//        // 创建OSSClient实例。
//    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//    // Endpoint以杭州为例，其它Region请按实际情况填写。
//    String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//    String accessKeyId = "<yourAccessKeyId>";
//    String accessKeySecret = "<yourAccessKeySecret>";
//
//    // 创建OSSClient实例。
//    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//    // 创建PutObjectRequest对象。
//    PutObjectRequest putObjectRequest = new PutObjectRequest("<yourBucketName>", "<yourObjectName>", new File("<yourLocalFile>"));
//
//// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
//// ObjectMetadata metadata = new ObjectMetadata();
//// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//// metadata.setObjectAcl(CannedAccessControlList.Private);
//// putObjectRequest.setMetadata(metadata);
//
//// 上传文件。
//ossClient.putObject(putObjectRequest);
//
//// 关闭OSSClient。
//ossClient.shutdown();


//    // Endpoint以杭州为例，其它Region请按实际情况填写。
//    String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//    String accessKeyId = "<yourAccessKeyId>";
//    String accessKeySecret = "<yourAccessKeySecret>";
//    String bucketName = "<yourBucketName>";
//
//    // 创建OSSClient实例。
//    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//    // 创建存储空间。
//    ossClient.createBucket(bucketName);
//
//    // 关闭OSSClient。
//    ossClient.shutdown();


