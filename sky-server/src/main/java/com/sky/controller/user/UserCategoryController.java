package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/category")
@Api(tags = "分类")
public class UserCategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("查询分类")
    public Result<List<Category>> list(Integer type) {
        log.info("查询分类：{}", type);
        List<Category> list = categoryService.selectByType(type);
        return Result.success(list);
    }

}
