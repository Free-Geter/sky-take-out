package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址簿管理")
@Slf4j
public class addressBookController {

    @Autowired
    AddressBookService addressBookService;

    @ApiOperation("查询用户所有地址")
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        log.info("查询用户所有地址");
        List<AddressBook> book = addressBookService.list();
        return Result.success(book);
    }

    @ApiOperation("新增地址")
    @PostMapping
    public Result insert(@RequestBody AddressBook addressBook) {
        log.info("新增地址:{}", addressBook);
        addressBookService.insert(addressBook);
        return Result.success();
    }

    @ApiOperation("按照id查询地址")
    @GetMapping("/{id}")
    public Result<AddressBook> selectById(@PathVariable Long id) {
        log.info("查询地址,id:{}", id);
        AddressBook addressBook = addressBookService.selectById(id);
        return Result.success(addressBook);
    }

    @ApiOperation("修改地址")
    @PutMapping
    public Result update(@RequestBody AddressBook addressBook) {
        log.info("修改地址:{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    @ApiOperation("按照id删除地址")
    @DeleteMapping
    public Result deleteById(Long id) {
        log.info("删除地址:{}", id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    @ApiOperation("查询默认地址")
    @GetMapping("/default")
    public Result<AddressBook> selectDefault() {
        log.info("查询默认地址");
        AddressBook addressBook = addressBookService.selectDefault();
        return Result.success();
    }

    @ApiOperation("设置默认地址")
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址为:{}", addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success();
    }
}
