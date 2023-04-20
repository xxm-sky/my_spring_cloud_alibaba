package com.xxm.mapper;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper {

    Map<String, Object> getUserInfo(Integer id);
}
