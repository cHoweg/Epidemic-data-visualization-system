package com.zoll.vinfo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @AllArgsConstructor
@NoArgsConstructor
@TableName("worlddetail_illness")
public class WorldDataDetailBean implements Serializable {

    //国家疫情地区id
    private int provinceId ;
     //国家疫情地区
    private String provinceProvincename ;
    //国家现存确诊人数
    private int provinceConfirmedcount;
    //国家累计确诊治愈人数
    private int provinceCuredcount;
    //国家累计死亡人数
    private int provinceDeadcount;
    // 国家对应上级大洲id
    private int countryId ;

}
