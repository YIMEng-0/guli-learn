package com.luobin.serviceoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import com.luobin.serviceoss.service.OssService;
import com.luobin.serviceoss.utils.ConstPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.UUID;

/**
 * @author Doraemon
 * @date 2022/5/31 11:23 上午
 * @version 1.0
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 实现上传头像的方法，首先在接口中定义方法，然后在实现类中实现方法即可
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        System.out.println( "file 现在是： " + file);

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstPropertiesUtils.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 获取文件的名称
        String fileName = file.getOriginalFilename();

        /**
         * 上面的方式上传文件存在问题，传送名字相同的文件可能把前面已经上传好的文件覆盖掉，所以需要对文件夹名字特殊处理
         *
         * 在每个文件的前面加上去 uuid 使得每个文件具有唯一的文件名字那么久不会覆盖掉了，符合实际的应用
         */
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + fileName;

        String url;
        try {
            // 获取上传文件输入流
            // 将上传的文件按照输入流的形式使用
            InputStream inputStream = file.getInputStream();

            /**
             // 创建PutObject请求。
             // 第一个参数  Bucket 名称
             // 第二个参数 上传到 oss 文件路径以及文件名称
             // 第三个参数 上传文件的输入流
             ossClient.putObject(bucketName, fileName, inputStream);
             */

            /**
             * 上面的方式任然存在一个问题，文件都在根目录中，下面考虑可以根据时间将文件分类
             *
             * 上面的做法中，是直接传递文件到达根目录中的，可以使用上传的文件分类，按照时间建立文件夹
             */
            // 获取当期的日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭 ossClient
            ossClient.shutdown();

            // 上传之后的文件了路径返回
            url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
