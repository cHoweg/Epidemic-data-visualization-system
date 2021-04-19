package com.zoll.vinfo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoll.vinfo.bean.WorldDataDetailBean;
import com.zoll.vinfo.mapper.WorldDataDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorldDataDetailService extends ServiceImpl<WorldDataDetailMapper,WorldDataDetailBean> {

    @Autowired
    private WorldDataDetailMapper worldDataDetailMapper;

    // 根据省份ID查询省内数据
    public List<WorldDataDetailBean> findCityDataById(Integer province_id){

        return worldDataDetailMapper.findCityDetailDataById(province_id);
    }
}
