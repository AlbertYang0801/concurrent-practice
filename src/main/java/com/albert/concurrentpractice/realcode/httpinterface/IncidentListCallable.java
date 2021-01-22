package com.albert.concurrentpractice.realcode.httpinterface;

import com.albert.concurrentpractice.realcode.httpinterface.po.IncidentDataVO;
import com.albert.utils.bean.FrameSpringBeanUtil;
import com.albert.utils.jackson.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author Albert
 * @date 2020/11/17 下午4:14
 */
@Slf4j
public class IncidentListCallable extends DoemHandler implements Callable<List<IncidentDataVO>> {

    private Integer page;

    /**
     * 线程类中直接注入Bean，会报空指针，无法直接获取到容器中的Bean，需要手动获取
     */
    private DoemHandler doemHandler;

    public IncidentListCallable(Integer page) {
        this.page = page;
        //手动获取Bean
        this.doemHandler = FrameSpringBeanUtil.getBean(DoemHandler.class);
    }

    @Override
    public List<IncidentDataVO> call() {
        //调用接口
        return getHttpResultList(page);
    }

    public List<IncidentDataVO> getHttpResultList(Integer page) {
        List<IncidentDataVO> incidentDataList = Lists.newArrayList();
        //查询所有的事件列表
        String allIncidentListResult = pageHttpGetIncidentListResult(page);
        try {
            fillResult(allIncidentListResult, incidentDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incidentDataList;
    }

    private void fillResult(String result, List<IncidentDataVO> incidentDataList) {
        JsonNode jsonNode = JsonUtil.getJsonNode(result);
        if (Objects.isNull(jsonNode)) {
            return;
        }
        JsonNode dataNode = jsonNode.get("data");
        if (Objects.isNull(dataNode)) {
            return;
        }
        JsonNode listNode = dataNode.get("list");
        if (Objects.isNull(listNode)) {
            return;
        }
        listNode.forEach(node -> {
            IncidentDataVO incidentDataVO = IncidentDataVO.builder()
                    .id(node.get("id") == null ? "" : node.get("id").asText())
                    .incidentName(node.get("incidentName") == null ? "" : node.get("incidentName").asText())
                    .createdTime(node.get("createdTime") == null ? 0L : node.get("createdTime").asLong())
                    .updatedTime(node.get("updatedTime") == null ? 0L : node.get("updatedTime").asLong())
                    .build();
            incidentDataList.add(incidentDataVO);
        });
    }


}
