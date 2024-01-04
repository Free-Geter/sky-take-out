package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    public Integer countByCategoryId(Long categoryId);

    List<Setmeal> query(Setmeal setmeal);

    List<DishItemVO> selectDishBySetmeal(Long setmealId);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(operateType = OperationType.INSERT)
    @Insert("insert into setmeal (category_id, name, price, status, description, image, create_time, update_time, create_user, update_user) " +
            "values (#{categoryId},#{name},#{price},#{status},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(Setmeal setmeal);

    @AutoFill(operateType = OperationType.UPDATE)
    void update(Setmeal setmeal);


    SetmealVO selectById(Long id);

    @AutoFill(operateType = OperationType.UPDATE)
    @Update("update setmeal set status = #{status} where id = #{id}")
    void changeStatus(Long id, Integer status);

    void deleteBatch(ArrayList<Long> ids);

    @Select("select status from setmeal where id = #{id}")
    Integer selectStatusById(Long id);
}
