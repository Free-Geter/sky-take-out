package com.sky.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OSSService {
    String imageUpload(MultipartFile image) throws IOException;
}
