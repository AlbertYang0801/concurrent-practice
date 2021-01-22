package com.albert.concurrentpractice.realcode.httpinterface;

import com.albert.concurrentpractice.realcode.httpinterface.common.ConfUtil;
import com.albert.concurrentpractice.realcode.httpinterface.common.UrlList;
import com.albert.utils.http.HttpClientUtil;
import com.albert.utils.localdatetime.LocalDateTimeUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * @author Albert
 * @date 2020/10/27 10:49
 */
@Component
@Slf4j
public class DoemHandler {

    @Autowired
    public ConfUtil confUtil;

    public final static Integer PAGD_SIZE = 50;

    public static String sessionId = "";
    public static Long startTime = 0L;
    public static Long endTime = 0L;
    public static String docpHost;

    /**
     * 类初始化赋值
     * 直接使用@Value注解读取配置文件数据会报空指针，需要在类初始化时获取避免报空指针
     */
    @PostConstruct
    public void init() {
        docpHost = confUtil.getDocpHost();
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("accountId", confUtil.getDocpAccountId());
        return headers;
    }

    /**
     * 获取两周内的时间列表
     */
    public List<Long> getTwoWeekTimeList() {
        LocalDateTime endTime = LocalDateTimeUtils.getWeeHour();
        LocalDateTime startTime = LocalDateTimeUtils.plus(endTime, -13, ChronoUnit.DAYS);
        return LocalDateTimeUtils.getTimestampBetweenTwoTime(startTime, endTime, ChronoUnit.DAYS);
    }

    /**
     * 请求接口
     */
    public String pageHttpGetIncidentListResult(Integer page) {
        String result = "";
        try {
            String url = docpHost + UrlList.GET_INCIDENTLIST;
            result = HttpClientUtil.post(HttpClientUtil.getClient(), url, getIncidentListParams(page), getIncidentListHanders());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取根据事件请求事件列表接口请求头参数
     */
    private Map<String, String> getIncidentListHanders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Cookie", "docp_session_id=" + sessionId);
        return headers;
    }

    /**
     * 获取根据事件请求事件列表接口请求体参数
     */
    private Map<String, Object> getIncidentListParams(Integer page) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("incidentName", "");
        params.put("alertSources", "");

        //处理中、已解决
        String[] incidentProcessStatusList = new String[]{"PROCESSING","CLOSED"};
        params.put("incidentProcessStatus", incidentProcessStatusList);

        //紧急、灾难、恢复
        String[] incidentLevelList = new String[]{"CRITICAL","DISASTER","OK"};
        params.put("incidentLevels", incidentLevelList);

        params.put("currentPage", page);
        params.put("pageSize", PAGD_SIZE);
        params.put("beginTime", startTime);
        params.put("endTime", endTime);
        return params;
    }


}
