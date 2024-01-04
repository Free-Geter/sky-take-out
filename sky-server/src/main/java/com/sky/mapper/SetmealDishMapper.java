package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    @Select("select setmeal_id from setmeal_dish where dish_id = #{id}")
    List<Long> selectSetmealIdsByDishId(Long id);

    @Insert("insert into setmeal_dish (setmeal_id, dish_id, name, price, copies) " +
            "VALUES (#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void insert(SetmealDish setmealDish);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> selectDishBySetmealId(Long id);

    Integer selectDishWithStatus(Long id, Integer status);
}
