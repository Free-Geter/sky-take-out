package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车操作")
@Slf4j
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加到购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加到购物车:{}", shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询购物车")
    public Result<List<ShoppingCart>> list() {
        log.info("查询购物车");
        List<ShoppingCart> result = shoppingCartService.list();
        return Result.success(result);
    }

    @ApiOperation("删除一个商品")
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("删除一个商品:{}",shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }

    @ApiOperation("清空购物车")
    @DeleteMapping("clean")
    public Result clean(){
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }
}
