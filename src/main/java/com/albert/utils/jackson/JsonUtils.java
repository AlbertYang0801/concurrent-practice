package com.albert.utils.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//Nullable需要引入spring-web包

/**
 * @author Johnson
 * jackson处理json相关的工具类
 */
@Slf4j
public class JsonUtils {
    public static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转换为json字符串
     * @param obj
     * @return
     */
    @Nullable
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }
    
    @Nullable
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            log.error("json解析转换为Bean出错，传入的json字符串为: " + json, e);
            return null;
        }
    }
    
    @Nullable
    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static JsonNode getJsonNode(String json) {
    	try {
    		return mapper.readTree(json);
    	} catch (IOException e) {
    		log.error("getJsonNode出错：", e);
    		return null;
    	}
    }

    @Nullable
    public static Map<String, Object> objectToMap (Object obj) {

        Map<String, Object> mappedObject = null;
        try {
            mappedObject = mapper.convertValue(obj, Map.class);
        } catch (IllegalArgumentException e) {
            log.error("getJsonNode出错：", e);
            return null;
        }

        return mappedObject;
    }

    public static Map<String, Object> parseJsonPath (String jsonPath) {
        Map<String, Object> map = null;
        try {
            File json = new File(jsonPath);
            map = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            log.error("parse jsonFile error：", e);
            return null;
        }
        return map;
    }


        @Data
    class User{
        private String name;
        private Integer age;
    }
    public static void main(String[] args) {
        String json="{\"name\":\"Johnson\",\"age\":\"21\"}";
        Map<String, String> map = toMap(json, String.class, String.class);
        log.info("map: "+map);
        String j="[{\"name\":\"zhuxinjun\",\"age\":\"21\"},{\"name\":\"yangyang\",\"age\":\"32\"}]";
        List<Map<String, String>> maps = nativeRead(j, new TypeReference<List<Map<String, String>>>() {});
        for(Map<String, String> map1:maps){
            System.out.println("map1"+map1);
        }
        System.out.println("---------- jn ----------");
        JsonNode jn = getJsonNode(j);
        System.out.println(jn);
        System.out.println(jn.isArray());
        Iterator<JsonNode> iterator = jn.iterator();
        while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

        System.out.println("---------- node ----------");

        JsonNode node = getJsonNode(json);
        System.out.println(node.isArray());
        JsonNode name = node.get("name");
        System.out.println(name.textValue());
        System.out.println(node.has("name"));
        System.out.println(node.has("test"));

    }

}
