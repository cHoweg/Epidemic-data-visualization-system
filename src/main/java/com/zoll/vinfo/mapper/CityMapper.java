package com.zoll.vinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoll.vinfo.bean.CityBean;
import com.zoll.vinfo.bean.DataDetailBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityMapper extends BaseMapper<CityBean> {

    @Select("SELECT\n" + "city" + "FROM\n" + "citys\n" + "WHERE\n" + "\tcitys.province_id = #{province_id}")
    List<String> findCityByProvinceId(Integer province_id);


    @Select("SELECT\n" + "city_id" + "FROM\n" + "citys\n" + "WHERE\n" + "\tcitys.city = #{city}")
    Integer findCityIdByName(String city);
}
