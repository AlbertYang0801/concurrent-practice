package com.albert.concurrentpractice.realcode.httpinterface.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Albert
 * @date 2020/11/16 下午5:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDataVO {

    private String id;
    private String incidentName;
    private Long createdTime;
    private Long updatedTime;
    private String processStatus;
    private String level;



}
