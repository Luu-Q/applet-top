package com.applet.sms.entity.dto;

import lombok.Data;

/**
 * @description: todo
 * @author: LUNING
 * @create: 2019-04-17 11:26
 */
@Data
public class SkillUpgrade {
    /**
     * 任务规则id
     **/
    private Integer lv;

    /**
     * 任务名称
     **/
    private Integer appearNum;

    private Integer selectNum;
}
