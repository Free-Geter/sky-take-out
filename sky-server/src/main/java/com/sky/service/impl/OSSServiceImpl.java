package com.sky.service.impl;

import com.sky.properties.AliOssProperties;
import com.sky.service.OSSService;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class OSSServiceImpl implements OSSService {

    @Autowired
    AliOssUtil aliOssUtil;

    @Override
    public String imageUpload(MultipartFile image) throws IOException {

        String imageName = image.getOriginalFilename();
        String uuFileName = UUID.randomUUID().toString() + imageName.substring(imageName.lastIndexOf('.'));
        return aliOssUtil.upload(image.getBytes(), uuFileName);
    }
}
