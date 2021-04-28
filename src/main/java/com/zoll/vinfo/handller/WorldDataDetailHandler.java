package com.zoll.vinfo.handller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.WorldDataBean;
import com.zoll.vinfo.bean.WorldDataDetailBean;
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
public class WorldDataDetailHandler {

    public static String urlStr = "http://api.tianapi.com/txapi/ncovabroad/index?key=b6e21d6f0b9b0e0f9508f52d3f0f348e";//接口地址

    public static void main(String[] args) {
        getData();
    }

    public static List<WorldDataDetailBean> getData() {

        String respJson = HttpClientUtil.doGet(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);

        ArrayList newsList = (ArrayList) map.get("newslist");

        //用于保存提取的数据
        List<WorldDataDetailBean> result = new ArrayList<>();
        int countryId;
        int provinceId0 = 0;
        int provinceId1 = 0;
        int provinceId2 = 0;
        int provinceId3 = 0;
        int provinceId4 = 0;
        int provinceId5 = 0;
        int provinceId6 = 0;
        for (int i = 0; i < newsList.size(); i++) {
            WorldDataDetailBean worldDetailBean;

            Map worldData = (Map) newsList.get(i);
            String continents = (String) worldData.get("continents");

            switch (continents) {
                case "亚洲":
                    countryId = 0;
                    break;
                case "欧洲":
                    countryId = 1;
                    break;
                case "北美洲":
                    countryId = 2;
                    break;
                case "南美洲":
                    countryId = 3;
                    break;
                case "非洲":
                    countryId = 4;
                    break;
                case "大洋洲":
                    countryId = 5;
                    break;
                default:
                    countryId = 6;
            }

            String province_provinceName = (String) worldData.get("provinceName");
            double province_currentConfirmedCount = (Double) worldData.get("currentConfirmedCount");
            double province_confirmedCount = (Double) worldData.get("confirmedCount");
            double province_curedCount = (Double) worldData.get("curedCount");
            double province_deadCount = (Double) worldData.get("deadCount");

            switch (countryId) {
                case 0:
                    worldDetailBean = new WorldDataDetailBean(countryId, province_provinceName,
                            (int) province_currentConfirmedCount, (int) province_confirmedCount,
                            (int) province_curedCount, (int) province_deadCount, provinceId0);
                    provinceId0++;
                    break;
                case 1:
                    worldDetailBean = new WorldDataDetailBean(countryId, province_provinceName,
                            (int) province_currentConfirmedCount, (int) province_confirmedCount,
                            (int) province_curedCount, (int) province_deadCount, provinceId1);
                    provinceId1++;
                    break;
                case 2:
                    worldDetailBean = new WorldDataDetailBean(countryId, province_provinceName,
                            (int) province_currentConfirmedCount, (int) province_confirmedCount,
                            (int) province_curedCount, (int) province_deadCount, provinceId2);
                    provinceId2++;
                    break;
                case 3:
                    worldDetailBean = new WorldDataDetailBean(countryId, province_provinceName,
                            (int) province_currentConfirmedCount, (int) province_confirmedCount,
                            (int) province_curedCount, (int) province_deadCount, provinceId3);
                    provinceId3++;
                    break;
                case 4:
                    worldDetailBean = new WorldDataDetailBean(countryId, province_provinceName,
                            (int) province_currentConfirmedCount, (int) province_confirmedCount,
                            (int) province_curedCount, (int) province_deadCount, provinceId4);
                    provinceId4++;
                    break;
                case 5:
                    worldDetailBean = new WorldDataDetailBean(countryId, province_provinceName,
                            (int) province_currentConfirmedCount, (int) province_confirmedCount,
                            (int) province_curedCount, (int) province_deadCount, provinceId5);
                    provinceId5++;
                    break;
                default:
                    worldDetailBean = new WorldDataDetailBean(countryId, province_provinceName,
                            (int) province_currentConfirmedCount, (int) province_confirmedCount,
                            (int) province_curedCount, (int) province_deadCount, provinceId6);
                    provinceId6++;
                    break;
            }
            result.add(worldDetailBean);
        }
        return result;
    }
}
