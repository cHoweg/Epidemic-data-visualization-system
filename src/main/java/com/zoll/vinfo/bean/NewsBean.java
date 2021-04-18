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
public class NewsBean {

    // 时间
    private String pubDateStr;
    // 标题
    private String title;
    // 内容
    private String summary;
    // 来源
    private String infoSource;
    // 链接
    private String sourceUrl;
}
