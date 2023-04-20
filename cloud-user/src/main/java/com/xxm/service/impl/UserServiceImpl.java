package com.xxm.service.impl;

import com.xxm.error.ServiceException;
import com.xxm.mapper.UserMapper;
import com.xxm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;


    @Override
    public Map<String, Object> getUserInfo(Integer id) {
        Map<String, Object> userInfo = userMapper.getUserInfo(id);
        if (userInfo == null || userInfo.isEmpty()) {
            throw new ServiceException("不存在数据");
        }
        return userInfo;

    }
}
