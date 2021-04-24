package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.TravelBean;
import com.zoll.vinfo.service.CityService;
import com.zoll.vinfo.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: Epidemic data visualization system
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2021/4/17 上午11:57
 */
public class TravelHandler {

    //public static String key="2a045db4716657a67be6f8340405c6f8";
    // public static int from=xxxxx;  出发的城市 入参为城市id
    // public static int to=xxxxx;    目的的城市 入参为城市id

    public static void main(String[] args) {
        System.out.println(getData(10009,10030));
        // for (int i=10001;i<=10360;i++){
        //String urlStr="http://apis.juhe.cn/springTravel/query?key= + key + &from=+xxxx+ &to=+ xxxxx;;
        //System.out.println(urlStr);
    }


    public static List<TravelBean> getData(Integer from_id, Integer to_id) {

        /**
         * 分析json字符串对数据进行筛选和提取
         */

        String urlStr = "http://apis.juhe.cn/springTravel/query?key=2a045db4716657a67be6f8340405c6f8&from=" + from_id + "&to=" + to_id;
        String respJson = HttpClientUtil.doGet(urlStr);
        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        //ArrayList arrayList = (ArrayList) map.get("result");
        Map mapNext = (Map) map.get("result");
        //System.out.println(mapNext);
        //System.out.println(mapNext.getClass().toString());
        List<TravelBean> result = new ArrayList<>();

        Map from_info = (Map) mapNext.get("from_info");
        String from_city_name = (String) from_info.get("city_name");
        String from_health_code_desc = (String) from_info.get("health_code_desc");
        String from_health_code_gid = (String) from_info.get("health_code_gid");
        String from_health_code_name = (String) from_info.get("health_code_name");
        String from_health_code_picture = (String) from_info.get("health_code_picture");
        String from_high_in_desc = (String) from_info.get("high_in_desc");
        String from_low_in_desc = (String) from_info.get("low_in_desc");
        String from_out_desc = (String) from_info.get("out_desc");
        String from_province_name = (String) from_info.get("province_name");
        //System.out.println(from_low_in_desc);


        Map to_info = (Map) mapNext.get("to_info");
        String to_city_name = (String) to_info.get("city_name");
        String to_health_code_desc = (String) to_info.get("health_code_desc");
        String to_health_code_gid = (String) to_info.get("health_code_gid");
        String to_health_code_name = (String) to_info.get("health_code_name");
        String to_health_code_picture = (String) to_info.get("health_code_picture");
        String to_high_in_desc = (String) to_info.get("high_in_desc");
        String to_low_in_desc = (String) to_info.get("low_in_desc");
        String to_out_desc = (String) to_info.get("out_desc");
        String to_province_name = (String) to_info.get("province_name");
        TravelBean travelBean = new TravelBean(
                from_city_name, from_health_code_desc, from_health_code_gid, from_health_code_name, from_health_code_picture, from_high_in_desc,
                from_low_in_desc, from_out_desc, from_province_name, to_city_name, to_health_code_desc, to_health_code_gid, to_health_code_name,
                to_health_code_picture, to_high_in_desc, to_low_in_desc, to_out_desc, to_province_name
        );
        result.add(travelBean);

        return result;
    }

}



