package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;


    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.query(setmeal);
    }

    @Override
    public List<DishItemVO> selectDishBySetmeal(Long setmealId) {
        return setmealMapper.selectDishBySetmeal(setmealId);
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> p = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    @Transactional
    public void insert(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
            setmealDishMapper.insert(setmealDish);
        }
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        // 修改套餐下的所有菜品
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDTO.getId());
            setmealDishMapper.insert(setmealDish);
        }

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 修改套餐信息
        setmealMapper.update(setmeal);
    }

    @Override
    public SetmealVO selecetById(Long id) {
        SetmealVO setmealVO = setmealMapper.selectById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.selectDishBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        if (status == StatusConstant.ENABLE)
            if (setmealDishMapper.selectDishWithStatus(id, StatusConstant.DISABLE) == 0)
                setmealMapper.changeStatus(id, status);
            else
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
        else
            setmealMapper.changeStatus(id, status);
    }

    @Override
    public void deleteBatch(ArrayList<Long> ids) {
        for (Long id : ids) {
            if (setmealMapper.selectStatusById(id) == StatusConstant.ENABLE)
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        setmealMapper.deleteBatch(ids);
    }
}
