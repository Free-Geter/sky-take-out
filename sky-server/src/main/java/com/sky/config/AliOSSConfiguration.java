package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliOSSConfiguration {

    @Bean
    @ConditionalOnMissingBean   // 保证工具类bean是单例的
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {   // 通过形参注入AliOssProperties类型的bean
        return new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getBucketName());
    }
}
