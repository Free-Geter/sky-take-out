package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    List<Dish> query(Dish dish);

    @Select("select count(id) from dish where category_id = #{categoryId}")
    public Integer countByCategoryId(Long categoryId);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Options(useGeneratedKeys = true, keyProperty = "id")   // 获取自动生成的主键，并将其赋值给dish.id 这里的keyProperty需要dish对象的属性名
    @AutoFill(operateType = OperationType.INSERT)
    @Insert("insert into dish(name, category_id, price, image, description, status ,create_time, update_time, create_user, update_user)" +
            "values (#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Dish dish);

    void deleteBatch(List<Long> ids);


    @AutoFill(operateType = OperationType.UPDATE)
    void update(Dish dish);

    @Select("select * from dish where id = #{id}")
    DishVO selectById(Long id);

}
