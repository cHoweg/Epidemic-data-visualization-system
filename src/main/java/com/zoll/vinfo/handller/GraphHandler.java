package com.zoll.vinfo.handller;

import com.zoll.vinfo.bean.GraphPieBean;
import com.google.gson.Gson;
import com.zoll.vinfo.bean.GraphAddBean;
import com.zoll.vinfo.bean.GraphBean;
import com.zoll.vinfo.bean.GraphColumnarBean;
import com.zoll.vinfo.util.HttpClientUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取图像信息网站的内容
 */
public class GraphHandler {


    public static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";
    public static String worldUrlStr = "https://api.inews.qq.com/newsqa/v1/automation/modules/list?modules=FAutoGlobalStatis,FAutoContinentStatis,FAutoGlobalDailyList,FAutoCountryConfirmAdd";
    public static String urlStrAll = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";


    public static void main(String[] args) {
        Map worldNowData = getWorldNowData();
        System.out.println(worldNowData);
    }

    public static String getData() {
        return HttpClientUtil.doGet(urlStr);
    }

    public static List<List<GraphBean>> getGraphData() {
        return getGraphData(getData());
    }


    public static Map getNowData() {
        String str = HttpClientUtil.doGet(urlStrAll);
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);

        // 此时增加了一层处理  而且data对应的数据格式是string
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);

        Map chinaTotal = (Map) subMap.get("chinaTotal");
        return chinaTotal;
    }

    public static Map getWorldNowData() {
        String worldStr = HttpClientUtil.doGet(worldUrlStr);
        Gson gson = new Gson();
        Map json = gson.fromJson(worldStr, Map.class);
        Map strMap = (Map) json.get("data");
        Map worldToday = (Map) strMap.get("FAutoGlobalStatis");
        return worldToday;
    }

    public static List<List<GraphBean>> getGraphData(String str) {
        List<List<GraphBean>> result = new ArrayList<>();
        Gson gson = new Gson();

        String worldStr = HttpClientUtil.doGet("https://view.inews.qq.com/g2/getOnsInfo?name=disease_foreign");

        Map mapData = gson.fromJson(worldStr, Map.class);
        String sub_Str = (String) mapData.get("data");
        Map sub_Map = gson.fromJson(sub_Str, Map.class);
        ArrayList globalDailyHistory = (ArrayList) sub_Map.get("globalDailyHistory");
        ArrayList<GraphBean> worldConfirm = new ArrayList<>();

        for (int i = 0; i < globalDailyHistory.size(); i++) {
            Map tmp = (Map) globalDailyHistory.get(i);
            Map all = (Map) tmp.get("all");
            String date = (String) tmp.get("date");
            double nowConfirm = (Double) all.get("confirm");
            GraphBean graphBean = new GraphBean(date, (int) nowConfirm);
            worldConfirm.add(graphBean);
        }


        Map map = gson.fromJson(str, Map.class);
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);
        ArrayList list = (ArrayList) subMap.get("chinaDayList");
        ArrayList<GraphBean> chinaConfirm = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map tmp = (Map) list.get(i);

            String date = (String) tmp.get("date");
            double nowConfirm = (Double) tmp.get("nowConfirm");
            GraphBean graphBean = new GraphBean(date, (int) nowConfirm);
            chinaConfirm.add(graphBean);
        }

        result.add(worldConfirm);
        result.add(chinaConfirm);
        return result;
    }


    public static List<List<GraphAddBean>> getGraphAddData() {
        return getGraphAddData(getData());
    }

    public static List<List<GraphAddBean>> getGraphAddData(String str) {
        Gson gson = new Gson();

        String worldStr = HttpClientUtil.doGet(worldUrlStr);
        ArrayList<GraphAddBean> worldAdd = new ArrayList<>();
        Map json = gson.fromJson(worldStr, Map.class);
        Map strMap = (Map) json.get("data");
        ArrayList arrayList = (ArrayList) strMap.get("FAutoGlobalDailyList");
        for (int i = 0; i < arrayList.size(); i++) {
            Map tmp = (Map) arrayList.get(i);
            String date = (String) tmp.get("date");
            Map subTmp = (Map) tmp.get("all");
            double addConfirm = (Double) subTmp.get("newAddConfirm");
            double addHeal = (Double) subTmp.get("heal");
            GraphAddBean graphAddBean = new GraphAddBean(date, (int) addConfirm, (int) addHeal);
            worldAdd.add(graphAddBean);
        }


        List<GraphAddBean> chinaAdd = new ArrayList<>();
        Map map = gson.fromJson(str, Map.class);

        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);

        ArrayList list = (ArrayList) subMap.get("chinaDayAddList");

        for (int i = 0; i < list.size(); i++) {
            Map tmp = (Map) list.get(i);
            String date = (String) tmp.get("date");
            double addConfirm = (Double) tmp.get("confirm");
            double addSuspect = (Double) tmp.get("suspect");

            GraphAddBean graphAddBean = new GraphAddBean(date,
                    (int) addConfirm, (int) addSuspect);
            chinaAdd.add(graphAddBean);
        }


        List<List<GraphAddBean>> result = new ArrayList<>();
        result.add(chinaAdd);
        result.add(worldAdd);
        return result;
    }


    public static List<GraphColumnarBean> getGraphColumnarData() {
        List<GraphColumnarBean> result = new ArrayList<>();

        String respJson = HttpClientUtil.doGet(urlStrAll);

        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);

        // 此时增加了一层处理  而且data对应的数据格式是string
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);

        // System.out.println(map);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        ArrayList childrenList = (ArrayList) dataMap.get("children");


        for (int i = 0; i < childrenList.size(); i++) {

            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");

            ArrayList children = (ArrayList) tmp.get("children");
            for (int j = 0; j < children.size(); j++) {
                Map subTmp = (Map) children.get(j);
                if ("境外输入".equals(subTmp.get("name"))) {
                    Map total = (Map) subTmp.get("total");
                    double fromAbroad = (Double) total.get("confirm");

                    GraphColumnarBean bean = new GraphColumnarBean(name, (int) fromAbroad);
                    result.add(bean);
                }
            }
        }

        return result;

    }

    public static List<GraphPieBean> getGraphPieData() {
        return getGraphPieData(getData());
    }

    public static List<GraphPieBean> getGraphPieData(String str) {

        List<GraphPieBean> result = new ArrayList<>();

        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);

        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);

        Map dataMap = (Map) subMap.get("nowConfirmStatis");

        for (Object o : dataMap.keySet()) {
            String name = (String) o;
            switch (name) {
                case "gat":
                    name = "港澳台病例";
                    break;
                case "import":
                    name = "境外输入病例";
                    break;
                case "province":
                    name = "31省本土病例";
                    break;
            }

            double value = (Double) dataMap.get(o);
            name += ":" + (int) value + "例";

            GraphPieBean bean = new GraphPieBean(name, (int) value);
            result.add(bean);
        }

        return result;
    }

}
