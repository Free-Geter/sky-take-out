package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "分类管理")
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(String name, Integer page, Integer pageSize, Integer type) {
        log.info("分类分页查询：名称：{}，类别：{}", name, type);
        PageResult pageResult = categoryService.pageQuery(name, page, pageSize, type);
        return Result.success(pageResult);
    }

    @ApiOperation(value = "启用/禁用分类")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable("status") Integer status, Long id) {
        log.info("修改{}类别状态为{}", id, status);
        categoryService.changeStatus(id, status);
        return Result.success();
    }

    @ApiOperation(value = "修改分类")
    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @ApiOperation(value = "新增分类")
    @PostMapping
    public Result insert(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.insert(categoryDTO);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping
    public Result deleteById(Long id) {
        log.info("删除{}号分类", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    @ApiOperation(value = "按类型查询")
    @GetMapping("/list")
    public Result<List<Category>> selectByType(Integer type) {
        log.info("查询{}类别的所有分类", type);
        List<Category> categoryList =  categoryService.selectByType(type);
        return Result.success(categoryList);
    }
}
