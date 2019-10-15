package com.imooc.mybatis.dao;

import com.imooc.mybatis.dto.GoodsDTO;
import com.imooc.mybatis.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsAnnotationDAO {
    @Select("select * from babytun.t_goods where current_price between #{min} and #{max} order by current_price limit 0, #{limit}")
    List<Goods> selectByPriceRange(@Param("min") Float min, @Param("max") Float max, @Param("limit") Integer limit);

    @Insert("insert into babytun.t_goods(title, sub_title, original_cost, current_price, discount, is_free_delivery,category_id) " +
            "values (#{title},#{subTitle},#{originalCost},#{currentPrice},#{discount},#{isFreeDelivery},#{categoryId})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "goodsId", before = false, resultType = Integer.class)
    int insert(Goods goods);

    @Select("select * from t_goods")
    @Results({
            @Result(column = "goods_id", property = "goodsId", id = true),
            @Result(column = "title", property = "title"),
            @Result(column = "current_price", property = "currentPrice")
    })
    List<Goods> selectAll();
}
