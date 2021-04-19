package com.zoll.vinfo.handller;

import com.zoll.vinfo.bean.DataBean;
import com.zoll.vinfo.bean.DataDetailBean;
import com.zoll.vinfo.service.DataDetailService;
import com.zoll.vinfo.service.DataService;
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

     public static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    public static void main(String[] args) throws Exception {
        // getData();
    }


    public void saveData() {
        List<DataBean> dataBeans = JsoupHandler.getData();;
        List<DataDetailBean> dataDetailBeans = JsoupHandler.getDetailData();

        // 先将数据清空  然后存储数据
        dataService.remove(null);
        dataService.saveBatch(dataBeans);

        dataDetailService.remove(null);
        dataDetailService.saveBatch(dataDetailBeans);
    }

    // 配置定时执行的注解  支持cron表达式
    @Scheduled(cron = "*/10 * * * * ? ")
    public void updateData() {
        System.out.println("更新数据");
        saveData();
    }

}
