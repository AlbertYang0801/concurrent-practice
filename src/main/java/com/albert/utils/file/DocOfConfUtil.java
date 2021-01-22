package com.albert.utils.file;

import com.albert.utils.jackson.JsonUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 读取文件的工具类
 *
 * @author yjw
 * @date 2020/8/3 21:44
 */
@Slf4j
public class DocOfConfUtil {

    /**
     * 获取项目路径下的文件
     *
     * @param path
     * @return
     */
    public static String getContentInPath(String path) {
        File file = new File(path);
        try {
            return FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("read user message from file error", e);
        }
        return null;
    }

    /**
     * 处理数组类型的文件。
     *
     * @param path
     * @return
     */
    public static List<String> dealwithList(String path) {
        List<String> infoList = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                try {
                    String info = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
                    infoList = JsonUtil.toList(info, String.class);
                } catch (IOException e) {
                    log.error("read user message from file error", e);
                }
            }
        } catch (Exception e) {
            log.error("fetch Device from file error", e);
        }
        return infoList;
    }

    public static void fillCache(String path, Map<String, String> cache) {
        try {
            Map<String, Object> infoJson = getJsonMap(path);
            Set<Map.Entry<String, Object>> entrySet = infoJson.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                cache.put(entry.getKey(), String.valueOf(entry.getValue()));
                log.debug("the point_id is {}, the point info is {}", entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            log.error("fetch Device from file error", e);
        }
    }

    public static Map<String, Object> getCache(String path) {
        Map<String, Object> cache = Maps.newHashMap();
        try {
            Map<String, Object> infoJson = getJsonMap(path);
            Set<Map.Entry<String, Object>> entrySet = infoJson.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                cache.put(entry.getKey(), entry.getValue());
                log.debug("the point_id is {}, the point info is {}", entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            log.error("fetch Device from file error", e);
        }
        return cache;
    }

    private static Map<String, Object> getJsonMap(String path) {
        Map<String, Object> infoJson = null;
        File file = new File(path);
        if (file.exists()) {
            try {
                String monitoringPointsInfo = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
                infoJson = JsonUtil.toMap(monitoringPointsInfo, String.class, Object.class);
            } catch (IOException e) {
                log.error("read user message from file error", e);
            }
        }
        return infoJson;
    }

    /**
     * 获取resource下的文件
     *
     * @param path
     * @return
     */
    public static String getResourceFile(String path) {
        try {
            //读取resource下的文件
            File file = ResourceUtils.getFile(path);
            return FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("read user message from file error", e);
        }
        return null;
    }


}
