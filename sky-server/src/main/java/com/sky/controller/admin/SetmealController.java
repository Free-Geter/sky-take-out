package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "套餐管理")
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;

    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        log.info("套餐分页查询{}", setmealPageQueryDTO);
        return Result.success(setmealService.pageQuery(setmealPageQueryDTO));
    }

    @ApiOperation(value = "新增套餐")
    @PostMapping
    public Result insert(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐{}", setmealDTO);
        setmealService.insert(setmealDTO);
        return Result.success();
    }

    @ApiOperation(value = "修改套餐")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @ApiOperation(value = "按id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> selectById(@PathVariable Long id) {
        log.info("查询套餐信息，id：{}", id);
        SetmealVO setmealVO = setmealService.selecetById(id);
        return Result.success(setmealVO);
    }

    @ApiOperation(value = "修改套餐状态")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable Integer status, Long id) {
        log.info("修改套餐状态,id：{}", id);
        setmealService.changeStatus(id, status);
        return Result.success();
    }

    @ApiOperation(value = "批量删除套餐")
    @DeleteMapping
    public Result deleteBatch(@RequestParam ArrayList<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }
}
