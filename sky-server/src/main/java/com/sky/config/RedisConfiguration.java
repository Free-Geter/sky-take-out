package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    // 方法名一定要是redisTemplate，或者在Bean注解中写明bean的名字为redisTemplate，否则注入的就不是自定义的redisTemplate对象
    // redisAutoConfiguration 类中也注入了一个名为 redisTemplate 的对象，并使用了ConditionalOnMissingBean(name = {"redisTemplate"})
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始实例化redis模板对象");
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置key的序列化器
        // 默认序列化器不便于在图像化界面查看key
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        System.out.println(redisTemplate);

        return redisTemplate;
    }
}
