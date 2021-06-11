package com.zoll.vinfo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @PROJECT_NAME: Epidemic data visualization system
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2021/4/17 上午11:59
 */

@Data
@AllArgsConstructor
public class RiskAreaBean {

    // 时间
    private String date;
    // 类型
    private String type;
    // 地方名字
    private String areaName;
    //街道
    private String communitys;


}
