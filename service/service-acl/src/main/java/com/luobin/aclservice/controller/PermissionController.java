package com.luobin.aclservice.controller;


import com.luobin.aclservice.entity.Permission;
import com.luobin.aclservice.service.PermissionService;

import com.luobin.common_utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取存在数据库中的所有菜单，使用递归的方式完成
     *  查询出来的结果需要构建成为树状结构，与分类的列表有一定的相似之处
     * @return
     */
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public R indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return R.ok().data("children",list);
    }

    /**
     * 删除菜单，递归删除菜单，因为在菜单中可能存在子菜单，子菜单中还是存在子菜单，所以使用了递归的方式
     * @param id
     * @return
     */
    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }
}

