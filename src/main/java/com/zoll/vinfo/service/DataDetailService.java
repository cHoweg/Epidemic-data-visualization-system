package com.zoll.vinfo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoll.vinfo.bean.DataDetailBean;
import com.zoll.vinfo.mapper.DataDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataDetailService extends ServiceImpl<DataDetailMapper,DataDetailBean> {

    @Autowired
    private DataDetailMapper dataDetailMapper;

    // 根据省份ID查询省内数据
    public List<DataDetailBean> findCityDataById(Integer province_id){
        return dataDetailMapper.findCityDetailDataById(province_id);
    }
}
