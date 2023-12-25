package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理相关")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation(value = "按照id查询")
    @GetMapping("/{id}")
    public Result<Employee> selectById(@PathVariable("id") Long id) {
        log.info("查询id为{}的员工信息", id);
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    @ApiOperation(value = "修改员工信息")
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改{}号员工信息", employeeDTO.getId());
        employeeService.update(employeeDTO);
        return Result.success();
    }

    @ApiOperation(value = "员工账号启/禁用")
    @PostMapping("/status/{status}")
    public Result ChangeStatus(Long id, @PathVariable Integer status) {
        log.info("修改{}员工状态为{}", id, status);
        employeeService.ChangeStatus(id, status);
        return Result.success();
    }

    @ApiOperation(value = "员工分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询：{}", employeePageQueryDTO);
        PageResult employeePageVo = employeeService.PageQuery(employeePageQueryDTO);
        return Result.success(employeePageVo);
    }


    @ApiOperation(value = "新增员工")
    @PostMapping
    public Result insert(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工 {}", employeeDTO);
        employeeService.insert(employeeDTO);
        return Result.success();
    }

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "员工登出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

}
