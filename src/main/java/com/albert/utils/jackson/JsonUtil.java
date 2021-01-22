package com.albert.utils.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Albert
 * @date 2020/7/30 15:55
 */
@Slf4j
public class JsonUtil {

    public static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转换为Json字符串
     *
     * @param object
     * @return
     */
    @Nullable
    public static String toString(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        if (object.getClass() == String.class) {
            return (String) object;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("object转换json解析出错：" + object, e);
            return null;
        }
    }

    /**
     * 将字符串序列化为对象
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    @Nullable
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("json解析出错，json转换为bean出错 ：" + json, e);
            return null;
        }
    }

    /**
     * 将json字符串转换为jsonnode
     *
     * @param json
     * @return
     */
    @Nullable
    public static JsonNode getJsonNode(String json) {
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将object对象转换为map
     *
     * @param object
     * @return
     */
    @Nullable
    public static Map<String, Object> objectToMap(Object object) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            return mapper.convertValue(object, Map.class);
        } catch (IllegalArgumentException e) {
            log.error("jackson object to map error : ", e);
            return null;
        }
    }

    /**
     * 将json字符串转换为list
     *
     * @param json
     * @param eClass
     * @param <E>
     * @return
     */
    @Nullable
    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转换为map
     * @param json
     * @param kClass
     * @param vClass
     * @param <K>
     * @param <V>
     * @return
     */
    @Nullable
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }



}
