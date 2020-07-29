package com.tml.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Description Jackson工具类
 * @Author TuMingLong
 * @Date 2020/3/5 19:40
 */
public class JacksonUtil {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //设置地点为中国
        objectMapper.setLocale(Locale.CHINA);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
        //去掉默认的时间戳格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        //序列化处理
        objectMapper.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
        objectMapper.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature());
        //失败处理
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //单引号处理
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.getSerializationConfig().withFeatures(
                SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

        // jdk8日期序列化和反序列化设置
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 将对象序列化json字符串
     *
     * @param t 对象可以是 String Map、List
     * @return json字符串
     */
    public static <T> String toJson(T t) {
        if (null == t) {
            return null;
        }

        try {
            return t instanceof String ? (String) t : objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转Java对象 JsonNode可适用本方法
     *
     * @param json
     * @param clazz
     * @return t
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || null == clazz) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T) json : objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json序列化
     *
     * @param json
     * @param typeReference
     * @return
     */
    public static <T> T toCollection(String json, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(json) || null == typeReference) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? json : objectMapper.readValue(json, typeReference));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取jsonNode对象
     *
     * @param json
     * @return
     */
    public static JsonNode getJsonNode(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, JsonNode.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
