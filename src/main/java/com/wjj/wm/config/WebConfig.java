package com.wjj.wm.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wjj.wm.common.utils.QkException;
import com.wjj.wm.filter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WebConfig
 * @Description MVC配置类
 * @Author weng_jun_ji_
 * @Date 2019/9/26 14:24
 * @Vervsion 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor interceptor;

    @Autowired
    public WebConfig(LoginInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加Login拦截器 匹配所有路径 /**
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    //跨域支持
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }

    //基于fastjson的自定义消息转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //添加fastjson的配置信息
        FastJsonConfig fJconfig = new FastJsonConfig();
        fJconfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteDateUseDateFormat);

        //处理中文乱码问题
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        //定义convert
        FastJsonHttpMessageConverter fJconverter = new FastJsonHttpMessageConverter();
        //在convert中添加配置信息
        fJconverter.setSupportedMediaTypes(mediaTypes);
        fJconverter.setFastJsonConfig(fJconfig);

        //添加到转换器中
        converters.add(fJconverter);
    }
}
