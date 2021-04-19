package com.zoll.vinfo.controller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.*;
import com.zoll.vinfo.handller.*;
import com.zoll.vinfo.service.DataDetailService;
import com.zoll.vinfo.service.DataService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 处理获取对应的相关数据信息封装到相应的bean对象里并且返回给前端
 */
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private DataDetailService dataDetailService;

    //首页显示所有相关数据
    @GetMapping("/")
    public String homePage(Model model) {

        List<DataBean> dataList = dataService.list();

        model.addAttribute("dataList", dataList);

        List<MapBean> result = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            DataBean dataBean = dataList.get(i);
            MapBean mapBean = new MapBean(dataBean.getArea(), dataBean.getNowConfirm());
            result.add(mapBean);

        }
        model.addAttribute("mapData", new Gson().toJson(result));

        String str = GraphHandler.getData();
        List<GraphBean> list = GraphHandler.getGraphData(str);
        //  进一步改造数据格式
        //  因为前端需要的数据是  x轴所有数据的数组和y轴所有数据的数组

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> nowConfirmList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            GraphBean graphBean = list.get(i);
            dateList.add(graphBean.getDate());
            nowConfirmList.add(graphBean.getNowConfirm());
        }

        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("nowConfirmList", new Gson().toJson(nowConfirmList));


        List<GraphAddBean> addList = GraphHandler.getGraphAddData(str);

        ArrayList<String> addDateList = new ArrayList<>();
        ArrayList<Integer> addConfirmList = new ArrayList<>();
        ArrayList<Integer> addSuspectList = new ArrayList<>();

        for (int i = 0; i < addList.size(); i++) {
            GraphAddBean graphAddBean = addList.get(i);
            addDateList.add(graphAddBean.getDate());
            addConfirmList.add(graphAddBean.getAddConfirm());
            addSuspectList.add(graphAddBean.getAddSuspect());
        }

        model.addAttribute("addDateList", new Gson().toJson(addDateList));
        model.addAttribute("addConfirmList", new Gson().toJson(addConfirmList));
        model.addAttribute("addSuspectList", new Gson().toJson(addSuspectList));


        List<GraphPieBean> pieList = GraphHandler.getGraphPieData(str);
        Collections.sort(pieList);
        model.addAttribute("pieList", new Gson().toJson(pieList));

        List<GraphColumnarBean> columnarList = GraphHandler.getGraphColumnarData();
        Collections.sort(columnarList);

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Integer> fromAbroadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GraphColumnarBean bean = columnarList.get(i);
            nameList.add(bean.getArea());
            fromAbroadList.add(bean.getFromAbroad());
        }

        model.addAttribute("nameList", new Gson().toJson(nameList));
        model.addAttribute("fromAbroadList", new Gson().toJson(fromAbroadList));

        return "homePage";
    }


    /**
     * 单个折线图显示现存确诊人数
     * @param
     * @return
     */
    @GetMapping("/graph")
    public ModelAndView graph() {
        ModelAndView modelAndView = new ModelAndView();
        List<GraphBean> list = GraphHandler.getGraphData();
        //  进一步改造数据格式
        //  因为前端需要的数据是  x轴所有数据的数组和y轴所有数据的数组

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> nowConfirmList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            GraphBean graphBean = list.get(i);
            dateList.add(graphBean.getDate());
            nowConfirmList.add(graphBean.getNowConfirm());
        }

        modelAndView.addObject("dateList", new Gson().toJson(dateList));
        modelAndView.addObject("nowConfirmList", new Gson().toJson(nowConfirmList));
        return modelAndView;
    }

    /**
     * 双重折线图显示新增确诊人数和疑似确诊人数
     * @param
     * @return
     */
    @GetMapping("/graphAdd")
    public ModelAndView graphAdd() {
        ModelAndView modelAndView = new ModelAndView();
        List<GraphAddBean> list = GraphHandler.getGraphAddData();

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> addConfirmList = new ArrayList<>();
        ArrayList<Integer> addSuspectList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            GraphAddBean graphAddBean = list.get(i);
            dateList.add(graphAddBean.getDate());
            addConfirmList.add(graphAddBean.getAddConfirm());
            addSuspectList.add(graphAddBean.getAddSuspect());
        }

        modelAndView.addObject("dateList", new Gson().toJson(dateList));
        modelAndView.addObject("addConfirmList", new Gson().toJson(addConfirmList));
        modelAndView.addObject("addSuspectList", new Gson().toJson(addSuspectList));
        return modelAndView;
    }

    /**
     * 显示全国排名前十的境外输入人数的条形统计图
     * @param
     * @return
     */
    @GetMapping("/graphColumnar")
    public ModelAndView graphColumnar() {
        ModelAndView modelAndView = new ModelAndView();
        List<GraphColumnarBean> list = GraphHandler.getGraphColumnarData();
        Collections.sort(list);

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Integer> fromAbroadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GraphColumnarBean bean = list.get(i);
            nameList.add(bean.getArea());
            fromAbroadList.add(bean.getFromAbroad());
        }

        modelAndView.addObject("nameList", new Gson().toJson(nameList));
        modelAndView.addObject("fromAbroadList", new Gson().toJson(fromAbroadList));
        return modelAndView;
    }


    /**
     * 饼状图
     * @param
     * @return
     */
    @GetMapping("/graphPie")
    public ModelAndView graphPie() {
        ModelAndView modelAndView = new ModelAndView();
        List<GraphPieBean> list = GraphHandler.getGraphPieData();
        Collections.sort(list);
        modelAndView.addObject("list", new Gson().toJson(list));
        return modelAndView;
    }


    /**
     * 根据地区名和确诊人数绘制中国地图
     * @param
     * @return
     */
    @GetMapping("/map")
    public ModelAndView map() {
        ModelAndView modelAndView = new ModelAndView();
        List<DataBean> list = dataService.list();

        List<MapBean> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DataBean dataBean = list.get(i);
            MapBean mapBean = new MapBean(dataBean.getArea(), dataBean.getNowConfirm());
            result.add(mapBean);
        }
        modelAndView.addObject("mapData", new Gson().toJson(result));
        modelAndView.setViewName("map");
        return modelAndView;
    }


    @GetMapping("/news")
    @ResponseBody
    public List<JSONObject> news(){
        List<JSONObject> data = NewsHandler.getData();
        return data;
    }

    @GetMapping("/rumors")
    @ResponseBody
    public String rumors(){
        ModelAndView modelAndView = new ModelAndView();
        List<RumorBean> RumorBeansList = RumorHandler.getData();
        modelAndView.addObject("rumorBeansList",RumorBeansList);
        return RumorBeansList.toString();
    }


    @GetMapping("/cityData")
    @ResponseBody
    public String cityData(Integer province_id){
        List<DataDetailBean> cityDataById = dataDetailService.findCityDataById(province_id);
        return cityDataById.toString();
    }

    @GetMapping("/worldData")
    @ResponseBody
    public String worldData(){
        ModelAndView modelAndView = new ModelAndView();
        List<WorldDataBean> worldDataBeanList= WorldDataHandler.getData();
        modelAndView.addObject("worldBeansList",worldDataBeanList);
        return worldDataBeanList.toString();
    }

    @GetMapping("/worldDataDetail")
    @ResponseBody
    public String worldDetailData(){
        ModelAndView modelAndView = new ModelAndView();
        List<WorldDataDetailBean> worldDataDetailBeanBeanList= WorldDataDetailHandler.getData();
        modelAndView.addObject("worldBeansList",worldDataDetailBeanBeanList);
        return worldDataDetailBeanBeanList.toString();
    }
}
