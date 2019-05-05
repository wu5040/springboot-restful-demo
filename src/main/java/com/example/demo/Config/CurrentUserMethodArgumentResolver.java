package com.example.demo.Config;

import com.example.demo.annotation.CurrentUser;
import com.example.demo.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String USER="user";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //检查该解析器是否支持参数类型 ，如果方法的参数里有CurrentUser注解 并且参数类型 是UserDTO 则返回true
        return methodParameter.hasParameterAnnotation(CurrentUser.class)&&methodParameter.getParameterType().isAssignableFrom(User.class);
    }


    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        User user=(User) nativeWebRequest.getAttribute(USER, RequestAttributes.SCOPE_REQUEST);
        if(user!=null){
            return user;
        }
        throw new MissingServletRequestPartException(USER);

    }
}
