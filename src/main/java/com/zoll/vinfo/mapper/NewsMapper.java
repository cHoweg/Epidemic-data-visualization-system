package com.zoll.vinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoll.vinfo.bean.NewsBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsMapper extends BaseMapper<NewsBean> {

    @Delete("DELETE FROM news WHERE id NOT IN \n" +
            "(SELECT * FROM (SELECT MIN(id) FROM news GROUP BY news.title HAVING COUNT(title)>1) B)\n" +
            " AND news.title IN (SELECT * FROM (SELECT news.title FROM  news GROUP BY news.title HAVING COUNT(title) > 1) A)")
    void filterData();
}
