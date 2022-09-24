package com.mofa.minio.controller;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${minio.endpoint:http://localhost:9000}")
    public String endpoint;
    @Value("${minio.accessKey}")
    public String accessKey;
    @Value("${minio.secretKey}")
    public String secretKey;
    @Value("${minio.bucketName}")
    public String bucketName;

    @Resource
    private MinioClient minioClient;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 上传
        String path = UUID.randomUUID().toString(); // 文件名，使用 UUID 随机
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName) // 存储桶
                .object(path) // 文件名
                .stream(file.getInputStream(), file.getSize(), -1) // 文件内容
                .contentType(file.getContentType()) // 文件类型
                .build());
        // 拼接路径
        return String.format("%s/%s/%s", endpoint, bucketName, path);
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    public void delete(@RequestParam("path") String path) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName) // 存储桶
                .object(path) // 文件名
                .build());
    }
}
