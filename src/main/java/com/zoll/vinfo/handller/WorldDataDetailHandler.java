package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.WorldDataBean;
import com.zoll.vinfo.bean.WorldDataDetailBean;
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
public class WorldDataDetailHandler {

    public static String urlStr = "https://api.yonyoucloud.com/apis/dst/ncov/wholeworld";//接口地址

    public static void main(String[] args) {
        getData();
    }

    public static List<WorldDataDetailBean> getData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        String respJson = newsUtil.getRequest(urlStr);

        //将数据转化成Map类型

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);

        Map data = (Map) map.get("data");
        //提取json中“data”字段里面的数据，并且通过get方法拿数据
        ArrayList continent = (ArrayList) data.get("continent");

        //用于保存提取的数据
        List<WorldDataDetailBean> result = new ArrayList<>();

        //for循环提取需要的字段，并且通过bean类 插入到list里面

        for (int i = 0; i < continent.size(); i++) {
            Map worlddata= (Map) continent.get(i);
           // String continentName = (String)worlddata.get("continentName");
            //double confirmedCount = (Double) worlddata.get("confirmedCount");
            //double curedCount = (Double)worlddata.get("curedCount");
            //double deadCount= (Double)worlddata.get("deadCount");
            ArrayList country= (ArrayList)worlddata.get("country");
            for (int j=0;j<country.size();j++){
                Map province =(Map) country.get(j);
                String province_provinceName = (String) province.get("provinceName");
                double province_confirmedCount = (Double) province.get("confirmedCount");
                double province_curedCount = (Double) province.get("curedCount");
                double province_deadCount = (Double) province.get("deadCount");
                //System.out.println(province_provinceName);
                //System.out.println(province.getClass().toString());
                //System.out.println("---------------------------");
                //System.out.println(continentName);
                //System.out.println(confirmedCount);
                //System.out.println(curedCount);
                //System.out.println(deadCount);

                WorldDataDetailBean worlddetailBean = new WorldDataDetailBean(j,province_provinceName,(int)province_confirmedCount,
                        (int)province_curedCount,(int)province_deadCount,i);
                //System.out.println(worlddetailBean);
                result.add(worlddetailBean);

            }

        }

        return result;
    }

}
