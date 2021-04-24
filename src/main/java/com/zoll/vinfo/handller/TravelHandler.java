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

    public static String riskUrl = "http://apis.juhe.cn/springTravel/risk?key=2a045db4716657a67be6f8340405c6f8";

    public static List<List<Map>> riskArea(){
        String respJson = HttpClientUtil.doGet(riskUrl);
        List<List<Map>> result = new ArrayList<>();

        ArrayList<Map> high_maps = new ArrayList<>();
        ArrayList<Map> middle_maps = new ArrayList<>();
        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        Map mapNext = (Map) map.get("result");
        if (mapNext.get("high_count") != "0"){
            ArrayList high_list = (ArrayList) mapNext.get("high_list");
            for (int i = 0; i < high_list.size(); i++) {
                Map highMap = (Map) high_list.get(i);
                high_maps.add(highMap);
            }
        }
        if (mapNext.get("middle_count") != "0"){
            ArrayList middle_list = (ArrayList) mapNext.get("middle_list");
            for (int i = 0; i < middle_list.size(); i++) {
                Map middleMap = (Map) middle_list.get(i);
                middle_maps.add(middleMap);
            }
        }
        result.add(high_maps);
        result.add(middle_maps);
        return result;
    }

}



