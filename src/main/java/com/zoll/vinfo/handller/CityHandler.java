package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.CityBean;
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
public class CityHandler {
    public static String urlStr = "http://apis.juhe.cn/springTravel/citys?key=2a045db4716657a67be6f8340405c6f8";

    public static void main(String[] args) {
        getData();
    }


    public static List<CityBean> getData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        String respJson = HttpClientUtil.doGet(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        //System.out.println(map22.getClass().toString());
        ArrayList arrayList = (ArrayList) map.get("result");
        //System.out.println(arrayList);
        //System.out.println(arrayList.getClass().toString());
        //System.out.println(arrayList.size());
        List<CityBean> result = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            Map tmp = (Map) arrayList.get(i);
            String province_id = (String) tmp.get("province_id");
            String province = (String) tmp.get("province");
            ArrayList citys = (ArrayList) tmp.get("citys");
            for (int j = 0; j < citys.size(); j++) {
                Map tmp2 = (Map) citys.get(j);
                String city_id = (String) tmp2.get("city_id");
                String city = (String) tmp2.get("city");
                //System.out.println(city_id);
                //System.out.println(city);
                CityBean cityBean = new CityBean(province_id, province, city_id, city);
                result.add(cityBean);
            }
        }
        return result;

    }

}


