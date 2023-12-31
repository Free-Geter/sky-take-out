package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult pageQuery(String name, Integer page, Integer pageSize, Integer type);

    void changeStatus(Long id, Integer status);

    void update(CategoryDTO categoryDTO);

    void insert(CategoryDTO categoryDTO);

    void deleteById(Long id);

    List<Category> selectByType(Integer type);
}
