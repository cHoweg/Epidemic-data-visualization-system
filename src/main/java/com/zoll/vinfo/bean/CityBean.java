package com.zoll.vinfo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("citys")
public class CityBean {

    //各省id
    private String provinceId;
    //各省
    private String province;
    //各城市id
    private String cityId;
    //各城市
    private String city;
}