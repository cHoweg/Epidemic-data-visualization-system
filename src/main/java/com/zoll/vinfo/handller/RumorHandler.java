package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.RumorBean;
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
public class RumorHandler {
    public static String urlStr = "https://api.yonyoucloud.com/apis/dst/ncov/identifyRumor";

    public static void main(String[] args) {
        getData();
    }

    public static List<RumorBean> getData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        String respJson = newsUtil.getRequest(urlStr);

        Gson gson = new Gson();
        Map map1 = gson.fromJson(respJson, Map.class);

        Map<String, String> map = new HashMap<String, String>();

        map = (Map<String, String>) gson.fromJson(respJson, map.getClass());
        HashMap date1 = new HashMap<>(map);
        ArrayList newslist = (ArrayList) date1.get("newslist");

        List<RumorBean> result = new ArrayList<>();

        for (int i = 0; i < newslist.size(); i++) {
            Map date2 = (Map) newslist.get(i);
            String date = date2.get("date").toString();
            String title = date2.get("title").toString();
            String explain = date2.get("explain").toString();
            RumorBean rumorBean = new RumorBean(date, title, explain);
            result.add(rumorBean);
        }

        return result;
    }

}
