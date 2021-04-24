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
    public static String urlStr = "http://apis.juhe.cn/springTravel/hsjg?key=2a045db4716657a67be6f8340405c6f8&city_id=10191";
    //public static String key="2a045db4716657a67be6f8340405c6f8";
    //public static int city_id=xxxxxx;

    public static void main(String[] args) {
        getData();
        // for (int i=10001;i<=10360;i++){
        //String urlStr="http://apis.juhe.cn/springTravel/hsjg?"+"key="+key+"&"+"city_id="+i;
        //System.out.println(urlStr);
        //String respJson = HttpClientUtil.doGet(urlStr);
        //Gson gson = new Gson();
        //Map map = gson.fromJson(respJson, Map.class);
        //Map map2 = (Map) map.get("result");
        //System.out.println(map2);
        //ArrayList data = (ArrayList) map2.get("data");
        //System.out.println(data);
        //for (int j = 0; j < data.size(); j++) {
            //Map tmp2 = (Map) data.get(j);
            //String name = (String) tmp2.get("name");
            //String phone = (String) tmp2.get("phone");
            //String address = (String) tmp2.get("address");
            //System.out.println(map.getClass().toString());

        }


        public static List<VaccinesBean> getData () {

            /**
             * 分析json字符串对数据进行筛选和提取
             */
            String respJson = HttpClientUtil.doGet(urlStr);

            Gson gson = new Gson();
            Map map = gson.fromJson(respJson, Map.class);
            //System.out.println(map22.getClass().toString());
            Map mapNext = (Map) map.get("result");
            //System.out.println(arrayList);
            //System.out.println(arrayList.getClass().toString());
            //System.out.println(arrayList.size());
            ArrayList arrayList = (ArrayList) mapNext.get("data");
            List<VaccinesBean> result = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                Map tmp = (Map) arrayList.get(i);
                String city_id = (String) tmp.get("city_id");
                String name = (String) tmp.get("name");
                String province = (String) tmp.get("province");
                String city = (String) tmp.get("city");
                String phone = (String) tmp.get("phone");
                String address = (String) tmp.get("address");
                VaccinesBean vaccinesBean = new VaccinesBean(city_id,city, province, name, phone, address);
                result.add(vaccinesBean);
                System.out.println(vaccinesBean);
            }
            return result;
        }

}



