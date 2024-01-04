package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "套餐")
public class UserSetmealController {

    @Autowired
    SetmealService setmealService;

    @ApiOperation(value = "根据分类id查询套餐")
    @GetMapping("/list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> SetmealList(Long categoryId) {
        Setmeal setmeal = Setmeal.builder().categoryId(categoryId).status(StatusConstant.ENABLE).build();
        List<Setmeal> setmeals = setmealService.list(setmeal);
        return Result.success(setmeals);
    }

    @ApiOperation("根据套餐id查询包含的菜品")
    @GetMapping("/dish/{id}")
    @Cacheable(cacheNames = "DishItemCache",key = "#setmealId")
    public Result<List<DishItemVO>> selectDishBySetmeal(@PathVariable("id") Long setmealId) {
        log.info("查询套餐{}内的菜品信息", setmealId);
        List<DishItemVO> dishItemVOS = setmealService.selectDishBySetmeal(setmealId);
        return Result.success(dishItemVOS);
    }
}
