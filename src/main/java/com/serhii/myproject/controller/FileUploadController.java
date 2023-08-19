package com.serhii.myproject.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    @Autowired
    private AmazonS3 s3client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file){
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentDisposition("attachment; filename=\""+ file.getOriginalFilename()
            + "\"");
            s3client
        }
    }

}
