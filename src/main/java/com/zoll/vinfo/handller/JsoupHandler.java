package com.zoll.vinfo.handller;


import com.google.gson.Gson;
import com.zoll.vinfo.bean.DataBean;
import com.zoll.vinfo.bean.DataDetailBean;
import com.zoll.vinfo.util.HttpURLConnectionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 使用jsoup对html页面进行爬取数据
 */
public class JsoupHandler {

    public static void main(String[] args) {
        getData();
    }

    public static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    /*
    public static String urlStr = "https://ncov.dxy.cn/ncovh5/view/pneumonia?" +
            "scene=2&from=singlemessage&isappinstalled=0";

    public static ArrayList<DataBean> getData() {

        ArrayList<DataBean> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urlStr).get();
            // 找到指定的标签数据
            Element oneScript = doc.getElementById("getAreaStat");

            String data = oneScript.data();
            // 字符串截取出json格式的数据
            String subData = data.substring(data.indexOf("["),
                    data.lastIndexOf("]") + 1);

            Gson gson = new Gson();
            ArrayList list = gson.fromJson(subData, ArrayList.class);

            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                String name = (String) map.get("provinceName");
                double nowConfirm = (Double) map.get("currentConfirmedCount");
                double confirm = (Double) map.get("confirmedCount");
                double heal = (Double) map.get("curedCount");
                double dead = (Double) map.get("deadCount");

                DataBean dataBean = new DataBean(name, (int) nowConfirm, (int) confirm
                        , (int) heal, (int) dead);
                result.add(dataBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    */

    public static List<DataBean> getData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        // 实时获取数据
        String respJson = HttpURLConnectionUtil.doGet(urlStr);
        // {"ret":0,"data":"{\"lastUpdateTime\":\"

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        // {ret=0.0, data={"lastUpdateTime":"2021-04-16 10:36:24"

        // 增加一层处理  而且data对应的数据格式是string
        String subStr = (String) map.get("data");
        // {"lastUpdateTime":"2021-04-16 10:36:24","chinaTotal":{"

        Map subMap = gson.fromJson(subStr, Map.class);
        //System.out.println(subMap);
        // {lastUpdateTime=2021-04-16 10:36:24, chinaTotal={confirm=103203.0,

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        // System.out.println(map);
        ArrayList childrenList = (ArrayList) dataMap.get("children");
        // System.out.println(childrenList);

        // 遍历然后转化
        List<DataBean> result = new ArrayList<>();

        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            // System.out.println(tmp);
            String name = (String) tmp.get("name");
            Map totalMap = (Map) tmp.get("total");
            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double confirm = (Double) totalMap.get("confirm");
            double heal = (Double) totalMap.get("heal");
            double dead = (Double) totalMap.get("dead");

            DataBean dataBean = new DataBean(name, (int) nowConfirm, (int) confirm, (int) heal, (int) dead);
            result.add(dataBean);


        }

        return result;
    }

    public static List<DataDetailBean> getDetailData() {

        /**
         * 分析json字符串对数据进行筛选和提取
         */
        // 实时获取数据
        String respJson = HttpURLConnectionUtil.doGet(urlStr);
        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);

        ArrayList childrenList = (ArrayList) dataMap.get("children");

        List<DataDetailBean> resultDetail = new ArrayList<>();
        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            //System.out.println(tmp);
            ArrayList childrenDetailList = (ArrayList) tmp.get("children");
            for (int j = 0; j < childrenDetailList.size(); j++) {
                Map city = (Map) childrenDetailList.get(j);
                String cityName = (String) city.get("name");
                Map cityTotalMap = (Map) city.get("total");
                double cityNowConfirm = (Double) cityTotalMap.get("nowConfirm");
                double cityConfirm = (Double) cityTotalMap.get("confirm");
                double cityHeal = (Double) cityTotalMap.get("heal");
                double cityDead = (Double) cityTotalMap.get("dead");

                DataDetailBean dataDetailBean = new DataDetailBean(cityName, (int) cityNowConfirm, (int) cityConfirm,
                        (int) cityHeal, (int) cityDead, i);
                resultDetail.add(dataDetailBean);
            }
        }

        return resultDetail;
    }


    @Test
    public void test() {
        String respJson = HttpURLConnectionUtil.doGet("https://lab.isaaclin.cn/nCoV/api/area?latest=0");
        //System.out.println(respJson);
        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        //System.out.println(map);

        ArrayList arrayList = (ArrayList) map.get("results");
        //System.out.println(arrayList);
    }

}
