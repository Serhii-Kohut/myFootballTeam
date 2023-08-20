package com.serhii.myproject.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {
    @Autowired
    private AmazonS3 s3client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        String photoUrl = uploadToS3(file);
        model.addAttribute("photoUrl", photoUrl);
        return "user-info";
    }

    public String uploadToS3(MultipartFile file) {
        long contentLength = file.getSize();
        String photoUrl = null;
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            objectMetadata.setContentDisposition("attachment; filename=\"" + file.getOriginalFilename() + "\"");
            s3client.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata));
            photoUrl = s3client.getUrl(bucketName, file.getOriginalFilename()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoUrl;
    }
}

