package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.DataBean;
import com.zoll.vinfo.bean.DataDetailBean;
import com.zoll.vinfo.service.DataDetailService;
import com.zoll.vinfo.service.DataService;
import com.zoll.vinfo.util.HttpURLConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
        List<DataDetailBean> dataDetailBeans = getData();
        // List<DataDetailBean> dataDetailBean = (List<DataDetailBean>) dataBeanList.get(0);

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


    public static List<DataDetailBean> getData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        // 实时获取数据
        String respJson = HttpURLConnectionUtil.doGet(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        // System.out.println(map);
        // 增加一层处理  而且data对应的数据格式是string
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);
        // System.out.println(map);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        // System.out.println(dataMap);

        ArrayList childrenList = (ArrayList) dataMap.get("children");
        // System.out.println(childrenList);

        // 遍历然后转化
        List<DataDetailBean> resultDetail = new ArrayList<>();

        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            System.out.println(tmp);
            ArrayList childrenDetailList = (ArrayList) tmp.get("children");
            for (int j = 0; j < childrenDetailList.size(); j++) {
                Map city = (Map) childrenDetailList.get(j);
                // System.out.println(tmp);
                String cityName = (String) city.get("name");
                // if (cityName.matches(".*输入.*") || cityName.matches(".*待确认.*")) continue;
                Map cityTotalMap = (Map) city.get("total");
                double cityNowConfirm = (Double) cityTotalMap.get("nowConfirm");
                double cityConfirm = (Double) cityTotalMap.get("confirm");
                double cityHeal = (Double) cityTotalMap.get("heal");
                double cityDead = (Double) cityTotalMap.get("dead");

                DataDetailBean dataDetailBean = new DataDetailBean(cityName, (int) cityNowConfirm, (int) cityConfirm,
                        (int) cityHeal, (int) cityDead, i);
                // System.out.println(dataDetailBean);
                resultDetail.add(dataDetailBean);
            }
        }

        return resultDetail;
    }
}
