package com.zoll.vinfo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoll.vinfo.bean.NewsBean;
import com.zoll.vinfo.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @PROJECT_NAME: Epidemic data visualization system
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2021/4/19 下午10:16
 */

@Service
public class NewsService extends ServiceImpl<NewsMapper, NewsBean> {

    @Autowired
    private NewsMapper newsMapper;

    // 删除重复数据
    public void filterData() {
        newsMapper.filterData();
        return;
    }
}
