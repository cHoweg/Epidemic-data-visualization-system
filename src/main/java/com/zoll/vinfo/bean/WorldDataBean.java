package com.zoll.vinfo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @AllArgsConstructor
@NoArgsConstructor
@TableName("world_illness")
public class WorldDataBean implements Serializable {

    //大洲疫情地区id
    private int countryId ;
    //大洲疫情地区
    private String continentName;
    //大洲现存确诊人数
    private int confirmedCount;
    //大洲累计确诊治愈人数
    private int curedCount;
    //大洲累计死亡人数
    private double deadCount;

}
