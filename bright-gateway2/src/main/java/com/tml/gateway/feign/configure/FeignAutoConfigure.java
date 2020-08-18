package com.tml.gateway.feign.configure;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * @description Feign OAuth2 request interceptor
 * @author TuMingLong
 * @sence 2020/5/9 15:14
 */
@Slf4j
@Configuration
public class FeignAutoConfigure {
    /**
     * 日志level有4个级别
     * 1.NONE，不记录任何日志。
     * 2.BASIC，仅记录请求方法和URL以及响应状态代码和执行时间。
     * 3.HEADERS，记录基本信息以及请求和响应标头。
     * 4.FULL,记录请求和响应的标题，正文和元数据
     */
    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder feignFormEncoder() {
        Encoder encoder = new SpringFormEncoder();
        log.info("-----注入Encoder-----");
        return encoder;
    }

    @Bean
    public Decoder feignDecoder() {
        log.info("-----注入Decoder-----");
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new GateWayMappingJackson2HttpMessageConverter());
        return new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return httpMessageConverters;
            }
        };
    }

    public class GateWayMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        GateWayMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = Lists.newArrayList();
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE  + ";charset=UTF-8"));
            setSupportedMediaTypes(mediaTypes);

            ObjectMapper objectMapper = new ObjectMapper();
            //序列化时，日期的统一格式
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            //去掉默认的时间戳格式
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            //序列化处理
            objectMapper.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
            objectMapper.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature());
            //忽略无法转换的对象
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            //反序列化时，属性不存在的兼容处理
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //允许单引号来包住属性名称和字符串值
            objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            //忽略为空的字段
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

            SimpleModule simpleModule = new SimpleModule();
            // Long类型序列化成字符串，避免Long精度丢失
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
            simpleModule.addSerializer(long.class, ToStringSerializer.instance);

            // jdk8日期序列化和反序列化设置
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));

            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));

            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));

            objectMapper.registerModule(javaTimeModule).registerModule(simpleModule);
            setObjectMapper(objectMapper);
        }
    }
}
