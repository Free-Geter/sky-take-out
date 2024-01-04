package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.ArrayList;
import java.util.List;

public interface SetmealService {
    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> selectDishBySetmeal(Long setmealId);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void insert(SetmealDTO setmealDTO);

    void update(SetmealDTO setmealDTO);

    SetmealVO selecetById(Long id);

    void changeStatus(Long id, Integer status);

    void deleteBatch(ArrayList<Long> ids);
}
