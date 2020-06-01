package kayleh.wizard.community.controller;

import com.aliyun.oss.OSSClient;
import kayleh.wizard.community.dto.FileDTO;
import kayleh.wizard.community.provider.AliCloudProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Wizard
 * @Date: 2020/5/31 13:43
 */
@Controller
@Slf4j
public class FileController {
    @Autowired
    private AliCloudProvider aliCloudProvider;



    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            //getOriginalFilename上传时的名字
            String fileName = aliCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());

            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (Exception e) {
            log.error("upload error", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }
    }
}

//@Controller
////@CrossOrigin // 解决跨域
//public class FileUploadController {
//    /**
//     * 上传文件
//     *
//     * @return
//     */
//
//    @RequestMapping("/file/upload")
//    @ResponseBody
//    public FileDTO uploadTeacherImg(HttpServletRequest request,
//                                    @RequestParam("file") MultipartFile file) {
//        // 地域节点
//        String endpoint = AliCloudProvider.ENDPOINT;
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = AliCloudProvider.KEYID;
//        String accessKeySecret = AliCloudProvider.KEYSECRET;
//        // BucketName
//        String yourBucketName = AliCloudProvider.BUCKETNAME;
//
//        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file1 = multipartHttpServletRequest.getFile("editormd-image-file");
//
//
//        try {
//            // 1.获取上传文件 MultipartFile file
//            // @RequestParam("file") file 与表单输入项的name值保持一致
//
//            // 2.获取上传文件名称，获取上传文件输入流
//            assert file1 != null;
//            String fileName = file1.getOriginalFilename();
//            // 在文件名称之前添加uuid值，保证文件名称不重复
//            String uuid = UUID.randomUUID().toString();
//
//            fileName = uuid + fileName;
//
//            // 获取当前日期 2020/01/03
//            String filePath = new DateTime().toString("yyyy/MM/dd");
//
//
//            // 拼接文件完整路径 2020/01/03/parker.jpg
//            fileName = filePath + "/" + fileName;
//
//            // 获取上传文件输入流
//            InputStream in = file1.getInputStream();
//
//            // 3.把上传文件存储到阿里云oss里面
//            // 创建OSSClient实例。
////            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//            // 上传文件流
//            // 第一个参数时BucketName，第二个参数是文件名称，第三个参数是输入流
//            ossClient.putObject(yourBucketName, fileName, in);
//
//            // 关闭OSSClient。
//            ossClient.shutdown();
//
//            // 返回上传之后的oss存储路径
//            String path = "http://" + yourBucketName + "." + endpoint + "/" + fileName;
//
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setUrl(filePath);
//            fileDTO.setSuccess(1);
//            fileDTO.setMessage("成功");
//            return fileDTO;
//        } catch (Exception e) {
//            e.printStackTrace();
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(0);
//            fileDTO.setMessage("失败");
//            return fileDTO;
//        }
//    }
//
//}
//@Controller
//public class FileUploadController {
//
//    @Autowired
//    private OSSClientUtil ossClientUtil;
//
//
//    //    @RequestMapping("/file/upload")
//    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> upload(HttpServletRequest request) throws IllegalStateException, IOException {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        Map<String, Object> result = new HashMap<>();
//
//
//        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
//        if (file != null) {
//            // 将图片上传到阿里云 OSS 对象云存储, 而后根据文件名生成图片链接
//            String name = ossClientUtil.uploadImg2Oss(file);
//            String url = ossClientUtil.getImgUrl(name);
//            // 本文使用的是 editor.Md 开源的 markdown 编辑器, 根据其文档说明 图片上传成功返回 json 数据给 editor.Md
//            result.put("success", 1);
//            result.put("message", "上传成功");
//            result.put("url", url);
//        }
////        // 判断 request 是否有文件上传, 即多部分请求
////        if (multipartResolver.isMultipart(request)) {
////            // 转换成多部分 request
////            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
////            // 取得 request 中的所有文件名
////            Iterator<String> iter = multiRequest.getFileNames();
////            while (iter.hasNext()) {
////                // 取得上传文件
////                MultipartFile f = multiRequest.getFile(iter.next());
////                if (f != null) {
////                    // 将图片上传到阿里云 OSS 对象云存储, 而后根据文件名生成图片链接
////                    String name = ossClientUtil.uploadImg2Oss(f);
////                    String imgUrl = ossClientUtil.getImgUrl(name);
////                    // 本文使用的是 editor.Md 开源的 markdown 编辑器, 根据其文档说明 图片上传成功返回 json 数据给 editor.Md
////                    result.put("success", 1);
////                    result.put("message", "上传成功");
////                    result.put("url", imgUrl);
////                }
////            }
////        }
//        else {
//            // 图片上传失败返回 json 数据给 editor.Md
//            result.put("success", 0);
//            result.put("message", "上传失败");
//        }
//        System.out.println(result.toString());
//        return result;
//    }
//}

//
//
//@Controller
//public class FileUploadController {
//
//    @Autowired
//    private OSSClientUtil ossClientUtil;
//
//
//        @RequestMapping("/file/upload")
////    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
//    @ResponseBody
//    public FileDTO upload(HttpServletRequest request) throws IllegalStateException, IOException {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
////        Map<String, Object> result = new HashMap<>();
//
//
//        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
//        if (file != null) {
//            // 将图片上传到阿里云 OSS 对象云存储, 而后根据文件名生成图片链接
//            String name = ossClientUtil.uploadImg2Oss(file);
//            String url = ossClientUtil.getImgUrl(name);
//            // 本文使用的是 editor.Md 开源的 markdown 编辑器, 根据其文档说明 图片上传成功返回 json 数据给 editor.Md
////            result.put("success", 1);
////            result.put("message", "上传成功");
////            result.put("url", url);
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(1);
//            fileDTO.setMessage("成功");
//            fileDTO.setUrl(url);
//            return fileDTO;
//        }
////        // 判断 request 是否有文件上传, 即多部分请求
////        if (multipartResolver.isMultipart(request)) {
////            // 转换成多部分 request
////            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
////            // 取得 request 中的所有文件名
////            Iterator<String> iter = multiRequest.getFileNames();
////            while (iter.hasNext()) {
////                // 取得上传文件
////                MultipartFile f = multiRequest.getFile(iter.next());
////                if (f != null) {
////                    // 将图片上传到阿里云 OSS 对象云存储, 而后根据文件名生成图片链接
////                    String name = ossClientUtil.uploadImg2Oss(f);
////                    String imgUrl = ossClientUtil.getImgUrl(name);
////                    // 本文使用的是 editor.Md 开源的 markdown 编辑器, 根据其文档说明 图片上传成功返回 json 数据给 editor.Md
////                    result.put("success", 1);
////                    result.put("message", "上传成功");
////                    result.put("url", imgUrl);
////                }
////            }
////        }
//        else {
//            // 图片上传失败返回 json 数据给 editor.Md
////            result.put("success", 0);
////            result.put("message", "上传失败");
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(0);
//            fileDTO.setMessage("失败");
//            return fileDTO;
//        }
////        System.out.println(result.toString());
//
//
//    }
//}