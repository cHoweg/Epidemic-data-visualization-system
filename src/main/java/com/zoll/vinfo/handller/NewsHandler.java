package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.NewsBean;
import com.zoll.vinfo.util.newsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: Epidemic data visualization system
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2021/4/17 上午11:57
 */
public class NewsHandler {
    public static String urlStr = "https://api.yonyoucloud.com/apis/dst/ncov/query";

    public static void main(String[] args) {
        // getData();
    }

    public static List<NewsBean> getData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        String respJson = newsUtil.getRequest(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);

        ArrayList arrayList = (ArrayList) map.get("newslist");
        Map mapData = (Map) arrayList.get(0);

        ArrayList news = (ArrayList) mapData.get("news");

        // 遍历然后转化
        List<NewsBean> result = new ArrayList<>();

        for (int i = 0; i < news.size(); i++) {
            Map tmp = (Map) news.get(i);
            // System.out.println(tmp);
            String pubDateStr = (String) tmp.get("pubDateStr");
            String title = (String) tmp.get("title");
            String summary = (String) tmp.get("summary");
            String infoSource = (String) tmp.get("infoSource");
            String sourceUrl = (String) tmp.get("sourceUrl");

            NewsBean newsBean = new NewsBean(pubDateStr, title, summary, infoSource, sourceUrl);
            result.add(newsBean);
        }

        return result;
    }

}
