package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    WeChatProperties weChatProperties;

    @Autowired
    UserMapper userMapper;

    @Override
    public User WXLogin(UserLoginDTO userLoginDTO) {
        // 向微信服务器提交请求，获取openId
        String openid = getWXOpenId(userLoginDTO.getCode());

        User user = userMapper.selectByOpenId(openid);

        // 用户已存在
        if (user != null)
            return user;
        else {
            // 否则插入新用户并返回其主键id
            user = User.builder().openid(openid).createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }

        return user;
    }

    private String getWXOpenId(String LoginCode) {
        HashMap<String, String> param = new HashMap<>();
        param.put("appid", weChatProperties.getAppid());
        param.put("secret", weChatProperties.getSecret());
        param.put("js_code", LoginCode);
        param.put("grant_type", "authorization_code");
        String response = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", param);

        // 登录失败
        if (response.contains("errcode"))
            throw new LoginFailedException(response.split("\"errmsg\":")[1]);

        // 登陆成功
        log.info("WX登录响应{}", response);
        JSONObject jsonObject = JSON.parseObject(response);
        String openid = jsonObject.getString("openid");


        return openid;
    }
}
