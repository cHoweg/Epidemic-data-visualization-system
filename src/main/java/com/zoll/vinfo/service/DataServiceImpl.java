package com.zoll.vinfo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zoll.vinfo.bean.DataBean;
import com.zoll.vinfo.mapper.DataMapper;
import org.springframework.stereotype.Service;


@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, DataBean>
        implements DataService {


}
