package com.zoll.vinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoll.vinfo.bean.WorldDataDetailBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorldDataDetailMapper extends BaseMapper<WorldDataDetailBean> {

    // select * from worlddetail_illness where countryId=输入参数
    @Select("SELECT\n" + "*" + "FROM\n" + "worlddetail_illness\n" + "WHERE\n" + "\t worlddetail_illness.province_id= #{provinceId}")
    List<WorldDataDetailBean> findCityDetailDataById(Integer province_id);

}
