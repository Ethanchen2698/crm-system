package com.ethan.crmsystem.web;

import com.ethan.crmsystem.service.RoleService;
import com.ethan.crmsystem.web.model.RoleModel;
import com.ethan.crmsystem.web.model.RoleTableModel;
import com.ethan.crmsystem.web.model.SelectGroupOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @version : RoleController.java,2019/11/6 23:58 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;

    @PostMapping("/addRole")
    public String addRole(@Validated RoleModel roleModel) {
        return roleService.addRole(roleModel);
    }

    @PostMapping("/editRole")
    public String editRole(@Validated RoleModel roleModel) {
        return roleService.editRole(roleModel);
    }

    @GetMapping("/getPriviledge/{roleId}")
    public List<String> getPriviledge(@PathVariable Integer roleId){
        return roleService.getRolePriviledge(roleId);
    }
    @GetMapping("/loadRoleTable")
    public List<RoleTableModel> loadRoleTable() {
        return roleService.loadRoleTable();
    }

    @DeleteMapping("/deleteRole/{id}")
    public String deleteRole(@PathVariable Integer id) {
        return roleService.deleteRole(id);
    }

    @GetMapping("/loadAllMetaRegion")
    public List<SelectGroupOption> loadAllMetaRegion() {
        return roleService.loadAllMetaRegion();
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
