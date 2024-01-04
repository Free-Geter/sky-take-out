package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理")
public class DishController {
    @Autowired
    DishService dishService;

    @ApiOperation(value = "菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageResult dishes = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(dishes);
    }

    @ApiOperation(value = "新增菜品")
    @PostMapping
    @CacheEvict(cacheNames = "DishCache", key = "#dishDTO.categoryId")
    public Result insert(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品{}", dishDTO);
        dishService.insert(dishDTO);
        return Result.success();
    }

    @ApiOperation(value = "批量删除菜品")
    @DeleteMapping
    @CacheEvict(cacheNames = "DishCache", allEntries = true)
    public Result deleteBatch(@RequestParam List<Long> ids) {
        log.info("批量删除菜品：{}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @ApiOperation(value = "修改菜品")
    @PutMapping
    @CacheEvict(cacheNames = "DishCache", key = "#dishDTO.categoryId")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    @ApiOperation(value = "按id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> selectById(@PathVariable Long id) {
        DishVO dish = dishService.selectById(id);
        return Result.success(dish);
    }

    @ApiOperation(value = "根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<Dish>> selectByCategoryId(Long categoryId) {
        List<Dish> dishes = dishService.selectByCategoryId(categoryId);
        return Result.success(dishes);
    }

    @ApiOperation(value = "菜品启/停售")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "DishCache", allEntries = true)
    public Result changeStatus(@PathVariable Integer status, Long id) {
        dishService.changeStatus(id, status);
        return Result.success();
    }
}
