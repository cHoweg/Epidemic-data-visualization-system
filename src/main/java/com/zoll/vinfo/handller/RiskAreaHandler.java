package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.RiskAreaBean;
import com.zoll.vinfo.bean.NewsBean;
import com.zoll.vinfo.util.HttpClientUtil;
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
public class RiskAreaHandler {
    public static String urlStr = "http://apis.juhe.cn/springTravel/risk?key=2a045db4716657a67be6f8340405c6f8";

    public static void main(String[] args) {
        getData();

    }

    public static List<RiskAreaBean> getData() {

        String respJson = HttpClientUtil.doGet(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        //System.out.println(map);


        Map arrayList = (Map) map.get("result");
        String date = (String) map.get("updated_date");
        //System.out.println(arrayList);
        List<RiskAreaBean> result = new ArrayList<>();
        ArrayList highList = (ArrayList) arrayList.get("high_list");
        for (int i = 0; i < highList.size(); i++) {
            Map tmp = (Map) highList.get(i);
            //System.out.println(tmp);
            //System.out.println(tmp.getClass());
            String type = "高风险地区";
            String areaName = (String) tmp.get("area_name");
            String communitys = tmp.get("communitys").toString();
            //System.out.println(areaName);
            //System.out.println(communitys);
            RiskAreaBean riskareaBean = new RiskAreaBean(date, type, areaName, communitys);
            result.add(riskareaBean);

        }
        //System.out.println(highList);
        ArrayList middleList = (ArrayList) arrayList.get("middle_list");
        //System.out.println(middleList.size());
        for (int i = 0; i < middleList.size(); i++) {
            Map tmp = (Map) middleList.get(i);
            //System.out.println(tmp);
            //System.out.println(tmp.getClass());
            String type = "中风险地区";
            String areaName = (String) tmp.get("area_name");
            String communitys = tmp.get("communitys").toString();
            //System.out.println(areaName);
            //System.out.println(communitys);
            RiskAreaBean riskareaBean = new RiskAreaBean(date, type, areaName, communitys);
            result.add(riskareaBean);

        }
        //System.out.println(result);
        return result;
    }

}
