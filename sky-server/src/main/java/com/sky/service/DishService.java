package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public interface DishService {
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void insert(DishDTO dishDTO);

    void deleteBatch(List<Long> ids);

    void update(DishDTO dishDTO);

    DishVO selectById(Long id);

    List<Dish> selectByCategoryId(Long categoryId);

    void changeStatus(Long id, Integer status);
}
