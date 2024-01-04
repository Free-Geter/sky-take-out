package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Page<Category> pageQuery(String name, Integer type);

    @AutoFill(operateType = OperationType.UPDATE)
    void update(Category category);

    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            "values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(operateType = OperationType.INSERT)
    void insert(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    List<Category> selectByType(Integer type);
}
