package com.zoll.vinfo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoll.vinfo.bean.CityBean;
import com.zoll.vinfo.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService extends ServiceImpl<CityMapper,CityBean> {

    @Autowired
    private CityMapper cityMapper;

    public List<String> findCityByProvinceId(Integer province_id){
        return cityMapper.findCityByProvinceId(province_id);
    }

    public Integer findCityIdByName(String city){
        return cityMapper.findCityIdByName(city);
    }
}
