package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.VaccinesBean;
import com.zoll.vinfo.util.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: Epidemic data visualization system
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2021/4/17 上午11:57
 */
public class VaccinesHandler {
    //public static String key="2a045db4716657a67be6f8340405c6f8";
    //public static int city_id=xxxxxx;

    public static void main(String[] args) {
        List<VaccinesBean> data = getData(10034);
        System.out.println(data);
    }


    public static List<VaccinesBean> getData(Integer id) {
        String urlStr = "http://apis.juhe.cn/springTravel/hsjg?key=2a045db4716657a67be6f8340405c6f8&city_id=";
        /**
         * 分析json字符串对数据进行筛选和提取
         */
        urlStr += id.toString();
        String respJson = HttpClientUtil.doGet(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        Map mapNext = (Map) map.get("result");
        ArrayList arrayList = (ArrayList) mapNext.get("data");
        List<VaccinesBean> result = new ArrayList<>();

        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                Map tmp = (Map) arrayList.get(i);
                String city_id = (String) tmp.get("city_id");
                String name = (String) tmp.get("name");
                String province = (String) tmp.get("province");
                String city = (String) tmp.get("city");
                String phone = (String) tmp.get("phone");
                String address = (String) tmp.get("address");
                VaccinesBean vaccinesBean = new VaccinesBean(city_id, city, province, name, phone, address);
                result.add(vaccinesBean);
            }
        }

        return result;
    }

}



