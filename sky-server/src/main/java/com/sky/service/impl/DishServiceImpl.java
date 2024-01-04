package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> dishVOS = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(dishVOS.getTotal(), dishVOS.getResult());
    }

    @Override
    @Transactional
    public void insert(DishDTO dishDTO) {
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        // 也可以通过dish的id获取插入后自动生成的主键
        Long dishId = dish.getId();
        if (dishFlavors != null && !dishFlavors.isEmpty())
            for (DishFlavor dishFlavor : dishFlavors) {
                dishFlavor.setDishId(dishId);
//                dishFlavorMapper.insert(dishFlavor);
            }
        dishFlavorMapper.insertBatch(dishFlavors);

    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            DishVO dishVO = dishMapper.selectById(id);
            if (Objects.equals(dishVO.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            } else {
                List<Long> setmealIds = setmealDishMapper.selectSetmealIdsByDishId(id);
                if (setmealIds != null && !setmealIds.isEmpty()) {
                    throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
                }
            }
        }
        dishFlavorMapper.deleteBatch(ids);
        dishMapper.deleteBatch(ids);
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        Long dishId = dishDTO.getId();
        dishFlavorMapper.deleteBatch(Collections.singletonList(dishDTO.getId()));
        if (flavors != null && !flavors.isEmpty()) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            // 只有非空时，才进行插入，否则会导致 insert into flavors values ; values后面没有参数，sql报错
            dishFlavorMapper.insertBatch(flavors);
        }

        dishMapper.update(dish);
    }

    @Override
    public DishVO selectById(Long id) {
        DishVO dishVO = dishMapper.selectById(id);
        List<DishFlavor> flavors = dishFlavorMapper.selectByDishId(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    public List<Dish> selectByCategoryId(Long categoryId) {
        Dish dish = Dish.builder().categoryId(categoryId).build();
        return dishMapper.query(dish);
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishes = dishMapper.query(dish);
        List<DishVO> dishVOS = new ArrayList<>();
        for (Dish dish1 : dishes) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish1, dishVO);
            dishVO.setFlavors(dishFlavorMapper.selectByDishId(dish1.getId()));
            dishVOS.add(dishVO);
        }
        return dishVOS;
    }
}
