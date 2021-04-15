package com.zoll.vinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoll.vinfo.bean.DataDetailBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDetailMapper extends BaseMapper<DataDetailBean> {

    List<DataDetailBean> findCityDetailDataById(Integer province_id);
}
