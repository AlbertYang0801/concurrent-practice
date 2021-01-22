package com.albert.concurrentpractice.realcode.httpinterface;

import com.albert.concurrentpractice.realcode.httpinterface.po.IncidentDataVO;
import com.albert.concurrentpractice.threadpool.ThreadPoolCreate;
import com.albert.utils.jackson.JsonUtil;
import com.albert.utils.localdatetime.LocalDateTimeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 使用线程池进行分页调用接口
 * @author Albert
 * @date 2020/10/27 10:53
 */
@Service
@Slf4j
public class DoemService extends DoemHandler {

    /**
     * 获取两周内30分钟以内的告警总和比率
     */
    public void getTwoWeekAlarmCount(String sessionIdValue) {
        //赋值sessionId
        sessionId = sessionIdValue;
        //时间周期
        List<Long> twoWeekTimeList = getTwoWeekTimeList();
        log.info("doem:获取到的时间列表为：{}", JsonUtil.toString(twoWeekTimeList));
        //接口请求的开始时间和结束时间
        startTime = twoWeekTimeList.get(0);
        endTime = LocalDateTimeUtils.getWeeHourMillis() + 86400000;

        List<IncidentDataVO> incidentDataList = Lists.newArrayList();
        //使用线程池调用调用接口获取数据
        httpGetIncidentList(incidentDataList);

    }

    private void httpGetIncidentList(List<IncidentDataVO> incidentDataList) {
        //第一次分页查询，获取总数
        Integer count = pageHttpGetIncidentList(1);
        int pageCount = count / PAGD_SIZE;
        if ((count % PAGD_SIZE) > 0) {
            pageCount++;
        }
        log.info("接口返回的总数为 :{}", pageCount);
        //使用线程池调用接口
        concurrentGetIncidentList(incidentDataList, pageCount);
    }

    /**
     * 并发调用接口
     */
    private void concurrentGetIncidentList(List<IncidentDataVO> incidentDataList, Integer pageCount) {
        List<Callable<List<IncidentDataVO>>> taskList = Lists.newArrayList();
        for (int i = 1; i <= pageCount; i++) {
            IncidentListCallable incidentListCallable = new IncidentListCallable(i);
            taskList.add(incidentListCallable);
        }
        //创建线程池
        ExecutorService executorService = ThreadPoolCreate.getThreadPoolByAlibaba(5, 20);
        List<IncidentDataVO> list = Lists.newArrayList();
        try {
            //使用线程池执行所有任务
            List<Future<List<IncidentDataVO>>> futures = executorService.invokeAll(taskList);
            futures.forEach(future -> {
                try {
                    //获取任务返回结果
                    List<IncidentDataVO> data = future.get();
                    list.addAll(data);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //关闭线程池
        executorService.shutdown();
        if (list.size() > 5) {
            log.info("doem：多线程调用之后获取到的结果为：{}", JsonUtil.toString(list.subList(0, 5)));
        }
        incidentDataList.addAll(list);
    }

    /**
     * 获取接口数据总数
     */
    public Integer pageHttpGetIncidentList(Integer page) {
        //查询所有的事件列表
        String allIncidentListResult = pageHttpGetIncidentListResult(page);
        return getIncidentListCount(allIncidentListResult);
    }

    /**
     * 获取接口返回值总数量
     */
    private Integer getIncidentListCount(String result) {
        JsonNode jsonNode = JsonUtil.getJsonNode(result);
        if (Objects.isNull(jsonNode)) {
            return 0;
        }
        JsonNode totalCount = jsonNode.findValue("totalCount");
        if (totalCount == null) {
            return 0;
        }
        int count = totalCount.asInt();
        log.info("获取事件列表的总记录数:{}", count);
        return count;
    }


}
