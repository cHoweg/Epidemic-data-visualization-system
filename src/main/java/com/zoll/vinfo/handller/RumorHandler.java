package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.NewsBean;
import com.zoll.vinfo.bean.RumorBean;
import com.zoll.vinfo.util.newsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        Map map = gson.fromJson(respJson, Map.class);

        Map<String, String> mapchange = new HashMap<String, String>();

        mapchange = (Map<String, String>) gson.fromJson(respJson, map.getClass());
        HashMap date_get=new  HashMap<>(mapchange);
        ArrayList newslist=(ArrayList)date_get.get("newslist");

        List<RumorBean> result = new ArrayList<>();

        for (int i = 0; i < newslist.size(); i++) {
            Map date_reget= (Map)newslist.get(i);
            String date=date_reget.get("date").toString();
            String title=date_reget.get("title").toString();
            String explain=date_reget.get("explain").toString();
            //System.out.println("---------------------------");
           //System.out.println(date);
            //System.out.println(title);
            //System.out.println(explain);
            RumorBean rumorBean = new RumorBean(date,title, explain);
            result.add(rumorBean);
        }

        return result;
    }

}
