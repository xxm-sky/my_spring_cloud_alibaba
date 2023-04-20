package com.xxm.interceptor;

import com.alibaba.fastjson.JSON;
import com.xxm.annotation.PassToken;
import com.xxm.bean.TokenInfo;
import com.xxm.bean.UserInfo;
import com.xxm.config.LoginInfo;
import com.xxm.consts.BaseConst;
import com.xxm.enums.ResultEnum;
import com.xxm.error.ServiceException;
import com.xxm.utils.JWTUtil;
import com.xxm.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;



@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION = "Authorization";
    private final RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        String token = request.getHeader(AUTHORIZATION);
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        PassToken passTokenBean = AnnotationUtils.findAnnotation(handlerMethod.getBean().getClass(), PassToken.class);
        //检查是否有passToken注释，有则跳过认证
        if (passTokenBean != null) return true;
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        if (token == null) throw new ServiceException(ResultEnum.NOT_TOKEN);
        TokenInfo tokenInfo = JWTUtil.parse(token);
        String obj = (String) redisUtil.get(BaseConst.TOKEN + tokenInfo.getTokenPath());
        if (obj == null) throw new ServiceException(ResultEnum.TOKEN_PAST);
        UserInfo userInfo = JSON.parseObject(obj, UserInfo.class);
        LoginInfo.setLoginInfo(userInfo);
        return verifyToken(token, userInfo);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

    }

    private boolean verifyToken(String reqToken, UserInfo userInfo) {
        if (!reqToken.equals(userInfo.getToken())) throw new ServiceException(ResultEnum.TOKEN_ERROR);
        return true;
    }
}
