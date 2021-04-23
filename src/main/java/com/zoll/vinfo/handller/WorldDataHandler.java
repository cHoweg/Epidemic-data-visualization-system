package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.RumorBean;
import com.zoll.vinfo.bean.WorldDataBean;
import com.zoll.vinfo.util.newsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: Epidemic data visualization system
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2021/4/17 上午11:57
 */
public class WorldDataHandler {
    public static String urlStr = "https://api.yonyoucloud.com/apis/dst/ncov/wholeworld";//接口地址

    public static void main(String[] args) {
        getData();
    }

    public static List<WorldDataBean> getData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        String respJson = newsUtil.getRequest(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);

        Map data = (Map) map.get("data");

        ArrayList continent = (ArrayList) data.get("continent");
        List<WorldDataBean> result = new ArrayList<>();

        for (int i = 0; i < continent.size(); i++) {
            Map worldData= (Map) continent.get(i);
            String continent_Name = (String)worldData.get("continentName");
            double confirmed_Count = (Double) worldData.get("confirmedCount");
            double cured_Count = (Double)worldData.get("curedCount");
            double dead_Count= (Double)worldData.get("deadCount");
            WorldDataBean worldBean = new WorldDataBean(i,continent_Name,(int)confirmed_Count, (int)cured_Count,(int)dead_Count);
            result.add(worldBean);
        }

        return result;
    }

}
