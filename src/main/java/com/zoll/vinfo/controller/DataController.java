package com.zoll.vinfo.controller;

import com.google.gson.Gson;
import com.zoll.vinfo.bean.*;
import com.zoll.vinfo.handller.GraphHandler;
import com.zoll.vinfo.handller.RumorHandler;
import com.zoll.vinfo.handller.TravelHandler;
import com.zoll.vinfo.handller.VaccinesHandler;
import com.zoll.vinfo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private WorldDataService worldDataService;

    @Autowired
    private WorldDataDetailService worldDataDetailService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CityService cityService;

    //首页显示所有相关数据
    @GetMapping("/")
    public String homePage() {

        /*List<DataBean> dataList = dataService.list();

        model.addAttribute("dataList", dataList);

        List<MapBean> result = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            DataBean dataBean = dataList.get(i);
            MapBean mapBean = new MapBean(dataBean.getArea(), dataBean.getNowConfirm());
            result.add(mapBean);
        }
        model.addAttribute("mapData", new Gson().toJson(result));

        String str = GraphHandler.getData();
        List<List<GraphBean>> list = GraphHandler.getGraphData(str);
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

*/

        return "homePage";
    }


    /**
     * 单个折线图显示现存确诊人数
     *
     * @param
     * @return
     */
    @GetMapping("/graph")
    public ModelAndView graph() {
        ModelAndView modelAndView = new ModelAndView();
        List<List<GraphBean>> list = GraphHandler.getGraphData();
        List<GraphBean> worldGraphBeans = list.get(0);
        List<GraphBean> graphBeans = list.get(1);
        //  进一步改造数据格式
        //  因为前端需要的数据是  x轴所有数据的数组和y轴所有数据的数组

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> nowConfirmList = new ArrayList<>();

        for (int i = 0; i < graphBeans.size(); i++) {
            GraphBean graphBean = graphBeans.get(i);
            dateList.add(graphBean.getDate());
            nowConfirmList.add(graphBean.getNowConfirm());
        }
        modelAndView.addObject("dateList", new Gson().toJson(dateList));
        modelAndView.addObject("nowConfirmList", new Gson().toJson(nowConfirmList));

        dateList.clear();
        nowConfirmList.clear();
        for (int i = 0; i < worldGraphBeans.size(); i++) {
            GraphBean graphBean = worldGraphBeans.get(i);
            dateList.add(graphBean.getDate());
            nowConfirmList.add(graphBean.getNowConfirm());
        }
        modelAndView.addObject("worldDateList", new Gson().toJson(dateList));
        modelAndView.addObject("worldNowConfirmList", new Gson().toJson(nowConfirmList));

        return modelAndView;
    }

    /**
     * 双重折线图显示新增确诊人数和疑似确诊人数
     *
     * @param
     * @return
     */
    @GetMapping("/graphAdd")
    public ModelAndView graphAdd() {
        ModelAndView modelAndView = new ModelAndView();
        List list = GraphHandler.getGraphAddData();
        List<GraphAddBean> chinaAdd = (ArrayList) list.get(0);
        List<GraphAddBean> worldAdd = (ArrayList) list.get(1);

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> addConfirmList = new ArrayList<>();
        ArrayList<Integer> addSuspectList = new ArrayList<>();

        for (int i = 0; i < chinaAdd.size(); i++) {
            GraphAddBean graphAddBean = chinaAdd.get(i);
            dateList.add(graphAddBean.getDate());
            addConfirmList.add(graphAddBean.getAddConfirm());
            addSuspectList.add(graphAddBean.getAddSuspect());
        }

        modelAndView.addObject("dateList", new Gson().toJson(dateList));
        modelAndView.addObject("addConfirmList", new Gson().toJson(addConfirmList));
        modelAndView.addObject("addSuspectList", new Gson().toJson(addSuspectList));

        dateList.clear();
        addConfirmList.clear();
        addSuspectList.clear();

        for (int i = 0; i < worldAdd.size(); i++) {
            GraphAddBean graphAddBean = worldAdd.get(i);
            dateList.add(graphAddBean.getDate());
            addConfirmList.add(graphAddBean.getAddConfirm());
            addSuspectList.add(graphAddBean.getAddSuspect());
        }
        modelAndView.addObject("worldDateList", new Gson().toJson(dateList));
        modelAndView.addObject("worldAddConfirmList", new Gson().toJson(addConfirmList));
        modelAndView.addObject("worldAddHealList", new Gson().toJson(addSuspectList));

        return modelAndView;
    }

    /**
     * 显示全国排名前十的境外输入人数的条形统计图
     *
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
     *
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
     *
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

        List<MapBean> count = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DataBean dataBean = list.get(i);
            MapBean mapBean = new MapBean(dataBean.getArea(), dataBean.getConfirm());
            count.add(mapBean);
        }

        modelAndView.addObject("mapCountData", new Gson().toJson(count));
        modelAndView.addObject("mapData", new Gson().toJson(result));
        modelAndView.setViewName("map");
        return modelAndView;
    }


    @GetMapping("/vaccines")
    public ModelAndView vaccines(@RequestParam(value = "lzy写", required = false) String name) {
        ModelAndView modelAndView = new ModelAndView();
        Integer id = cityService.findCityIdByName(name);
        List<VaccinesBean> VaccinesBeansList = VaccinesHandler.getData(id);
        modelAndView.addObject("vaccinesBeansList", VaccinesBeansList);
        modelAndView.setViewName("vaccines");
        return modelAndView;
    }

    @GetMapping("/travel")
    public ModelAndView travel(@RequestParam(value = "lzy写", required = false) String from_name, @RequestParam(value = "lzy写", required = false) String to_name) {
        ModelAndView modelAndView = new ModelAndView();
        Integer from_id = cityService.findCityIdByName(from_name);
        Integer to_id = cityService.findCityIdByName(to_name);
        List<TravelBean> TravelBeansList = TravelHandler.getData(from_id, to_id);
        modelAndView.addObject("vaccinesBeansList", TravelBeansList);
        modelAndView.setViewName("travel");
        return modelAndView;
    }


    @GetMapping("/news")
    public ModelAndView news() {
        ModelAndView modelAndView = new ModelAndView();
        List<NewsBean> newsBeanList = newsService.list();
        modelAndView.addObject("newBeanList", newsBeanList);
        return modelAndView;
    }

    @GetMapping("/rumors")
    public ModelAndView rumors() {
        ModelAndView modelAndView = new ModelAndView();
        List<RumorBean> RumorBeansList = RumorHandler.getData();
        modelAndView.addObject("rumorBeansList", RumorBeansList);
        modelAndView.setViewName("rumors");
        return modelAndView;
    }

    @GetMapping("/cityData")
    public ModelAndView cityData(@RequestParam(value = "select2", required = false) Integer province_id) {
        ModelAndView modelAndView = new ModelAndView();
        if (province_id == null) {
            List<DataBean> dataList = dataService.list();
            modelAndView.addObject("cityDataById", dataList);
        } else {
            List<DataDetailBean> cityDataById = dataDetailService.findCityDataById(province_id);
            modelAndView.addObject("cityDataById", cityDataById);
        }
        modelAndView.setViewName("provinceList");
        return modelAndView;
    }

    @GetMapping("/chinaData")
    public ModelAndView chinaData() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("provinceList");
        return modelAndView;
    }

    @GetMapping("/worldDataDetail")
    public ModelAndView worldDetailData(@RequestParam(value = "select3", required = false) Integer province_id) {
        ModelAndView modelAndView = new ModelAndView();
        List<WorldDataDetailBean> worldDataDetailBeanBeanList = worldDataDetailService.findCityDataById(province_id);
        modelAndView.addObject("worldBeansList", worldDataDetailBeanBeanList);

        modelAndView.setViewName("worldList");
        return modelAndView;
    }

    @GetMapping("/countryDetail")
    public ModelAndView countryData() {
        ModelAndView modelAndView = new ModelAndView();
        List<WorldDataDetailBean> worldDataDetailBeanBeanList = worldDataDetailService.list();
        modelAndView.addObject("worldBeansList", worldDataDetailBeanBeanList);
        modelAndView.setViewName("worldMap");
        return modelAndView;
    }
}
