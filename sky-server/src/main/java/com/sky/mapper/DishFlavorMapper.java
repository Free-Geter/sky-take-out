package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

//    @AutoFill(operateType = OperationType.INSERT)
    @Insert("insert into dish_flavor(dish_id, name, value) " +
            "values (#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);

    void insertBatch(List<DishFlavor> dishFlavors);

    void deleteBatch(List<Long> ids);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> selectByDishId(Long id);
}
