package com.zoll.vinfo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("citys")
public class TravelBean {

    //出发城市名字
    private String fromCityName;
    //出发城市健康码进入方式
    private String fromHealthCodeDesc;
    //出发城市健康码网址
    private String fromHealthCodeGid;
    //出发城市健康码名字
    private String fromHealthCodeName;
    //出发城市健康码图片
    private String fromHealthHodePicture;
    //出发城市-高风险地区回城政策
    private String fromHighInDesc;
    //出发城市-非高风险地区回城政策
    private String fromLowInDesc;
    //出发城市-离城政策
    private String fromOutDesc;
    //出发城市省份
    private String fromProvinceName;

    //目的城市名字
    private String toCityName;
    //目的城市健康码进入方式
    private String toHealthCodeDesc;
    //目的城市健康码网址
    private String toHealthCodeGid;
    //目的城市健康码名字
    private String toHealthCodeName;
    //目的城市健康码图片
    private String toHealthHodePicture;
    //目的城市-高风险地区回城政策
    private String toHighInDesc;
    //目的城市-非高风险地区回城政策
    private String toLowInDesc;
    //目的城市-离城政策
    private String toOutDesc;
    //目的城市省份
    private String toProvinceName;

}