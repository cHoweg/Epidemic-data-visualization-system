package com.zoll.vinfo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("citys")
public class VaccinesBean {

    //各省id
    private String cityId;
    //各城市
    private String city;
    //各省
    private String province;
    //名称
    private String name;
    //电话号码
    private String phone;
    //地点
    private String address;
}