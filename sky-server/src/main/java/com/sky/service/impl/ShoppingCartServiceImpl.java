package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartMapper shoppingCartMapper;
    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        Long setmealId = shoppingCartDTO.getSetmealId();
        Long dishId = shoppingCartDTO.getDishId();
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> shoppingCartList = shoppingCartMapper.query(shoppingCart);

        if (shoppingCartList != null && !shoppingCartList.isEmpty()) {
            ShoppingCart cart = shoppingCartList.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.addon(cart);
        } else {
            if (setmealId != null) {
                SetmealVO setmealVO = setmealMapper.selectById(setmealId);
                shoppingCart.setName(setmealVO.getName());
                shoppingCart.setImage(setmealVO.getImage());
                shoppingCart.setAmount(setmealVO.getPrice());
            } else {
                DishVO dishVO = dishMapper.selectById(dishId);
                shoppingCart.setName(dishVO.getName());
                shoppingCart.setImage(dishVO.getImage());
                shoppingCart.setAmount(dishVO.getPrice());
            }
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }

//        if (setmealId != null) {
//            SetmealVO setmealVO = setmealMapper.selectById(setmealId);
//            Integer count = shoppingCartMapper.selectByUserIdSetmealId(BaseContext.getCurrentId(), setmealId);
//            if (count != null && count > 0) {
//                shoppingCartMapper.addonSetmeal(BaseContext.getCurrentId(), setmealId);
//            } else {
//                shoppingCart.setAmount(setmealVO.getPrice());
//                shoppingCart.setImage(setmealVO.getImage());
//                shoppingCart.setName(setmealVO.getName());
//                shoppingCart.setNumber(1);
//                shoppingCart.setCreateTime(LocalDateTime.now());
//                shoppingCartMapper.insert(shoppingCart);
//            }
//        } else if (dishId != null) {
//            DishVO dishVO = dishMapper.selectById(dishId);
//            Integer count = shoppingCartMapper.selectByUserIdDishId(BaseContext.getCurrentId(), dishId);
//            if (count != null && count > 0) {
//                shoppingCartMapper.addonDish(BaseContext.getCurrentId(), dishId);
//            } else {
//                shoppingCart.setAmount(dishVO.getPrice());
//                shoppingCart.setImage(dishVO.getImage());
//                shoppingCart.setName(dishVO.getName());
//                shoppingCart.setNumber(1);
//                shoppingCart.setCreateTime(LocalDateTime.now());
//                shoppingCartMapper.insert(shoppingCart);
//            }
//        }
    }

    @Override
    public List<ShoppingCart> list() {
        return shoppingCartMapper.query(ShoppingCart.builder().userId(BaseContext.getCurrentId()).build());
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.query(shoppingCart);
        if (list != null && !list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            if (cart.getNumber() == 1)
                shoppingCartMapper.remove(shoppingCart);
            else {
                cart.setNumber(cart.getNumber() - 1);
                shoppingCartMapper.sub(cart);
            }
        }
    }

    @Override
    public void clean() {
        shoppingCartMapper.clean(BaseContext.getCurrentId());
    }

}
