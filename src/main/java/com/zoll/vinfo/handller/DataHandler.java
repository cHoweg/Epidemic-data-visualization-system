package com.zoll.vinfo.handller;

import com.zoll.vinfo.bean.*;
import com.zoll.vinfo.service.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 使用HttpURLConnection实时的从网站获取最新数据内容
 */

@Component
public class DataHandler {

    @Autowired
    private DataService dataService;

    @Autowired
    private DataDetailService dataDetailService;

    @Autowired
    private WorldDataService worldDataService;

    @Autowired
    private WorldDataDetailService worldDataDetailService;

    @Autowired
    private NewsService newsService;

    public static void main(String[] args) {
        List<WorldDataDetailBean> data = WorldDataDetailHandler.getData();
        System.out.println(data);
    }


    public void saveData() {
        List<DataBean> dataBeans = JsoupHandler.getData();
        List<DataDetailBean> dataDetailBeans = JsoupHandler.getDetailData();

        List<WorldDataBean> worldDataBeans = WorldDataHandler.getData();
        List<WorldDataDetailBean> worldDataDetailBeans = WorldDataDetailHandler.getData();

        List<NewsBean> newsBeans = NewsHandler.getData();

        // 先将数据清空  然后存储数据
        dataService.remove(null);
        dataService.saveBatch(dataBeans);

        dataDetailService.remove(null);
        dataDetailService.saveBatch(dataDetailBeans);

        worldDataService.remove(null);
        worldDataService.saveBatch(worldDataBeans);

        worldDataDetailService.remove(null);
        worldDataDetailService.saveBatch(worldDataDetailBeans);

        newsService.saveBatch(newsBeans);
        newsService.filterData();
    }

    // 配置定时执行的注解  支持cron表达式
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void updateData() {
        System.out.println("更新数据");
        saveData();
    }

}
