package com.zoll.vinfo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @AllArgsConstructor
@NoArgsConstructor
@TableName("detail_illness")
public class DataDetailBean implements Serializable {

    //疫情地区
    private String area;
    //现存确诊人数
    private int nowConfirm;
    //累计确诊人数
    private int confirm;
    //治愈人数
    private int heal;
    //死亡人数
    private int dead;
    //省份
    private int province_id;
}
