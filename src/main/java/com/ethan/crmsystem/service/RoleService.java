package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.domain.*;
import com.ethan.crmsystem.repository.*;
import com.ethan.crmsystem.web.model.RoleModel;
import com.ethan.crmsystem.web.model.RoleTableModel;
import com.ethan.crmsystem.web.model.SelectGroupOption;
import com.ethan.crmsystem.web.model.SelectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: RoleService.java, 2018/8/6 16:44 $
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class RoleService {

    private RoleRepository roleRepository;

    private RoleMenuRepository roleMenuRepository;

    private UserRepository userRepository;

    private MetaRegionRepository metaRegionRepository;

    private RolePriviledgeRepository rolePriviledgeRepository;

    private ElementUiService elementUiService;

    public String addRole(RoleModel roleModel) {
        Optional<Role> roleOptional = roleRepository.findByName(roleModel.getName());
        if (roleOptional.isPresent()) {
            return ResponseConstants.EXISTS;
        }

        Role role = new Role();
        role.setName(roleModel.getName());

        role = roleRepository.save(role);

        List<RoleMenu> roleMenus = new ArrayList<>();
        for (String menu : roleModel.getMenus()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(Integer.parseInt(menu));
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        }

        roleMenuRepository.saveAll(roleMenus);

        List<RolePriviledge> rolePriviledges = new ArrayList<>();
        for (String  regionId: roleModel.getRolePrividges()){
            RolePriviledge rolePriviledge = new RolePriviledge();
            rolePriviledge.setRoleId(role.getId());
            rolePriviledge.setMetaRegionId(regionId);
            rolePriviledges.add(rolePriviledge);
        }

        rolePriviledgeRepository.saveAll(rolePriviledges);

        return ResponseConstants.SUCCESS;
    }

    public List<RoleTableModel> loadRoleTable() {
        List<Role> roles = roleRepository.findAll();

        List<RoleTableModel> roleTableModels = new ArrayList<>();
        roles.forEach(role -> {
            RoleTableModel roleTableModel = new RoleTableModel();
            roleTableModel.setId(role.getId());
            roleTableModel.setName(role.getName());

            List<String> menuIds = new ArrayList<>();
            List<String> menuNames = new ArrayList<>();
            role.getRoleMenuList().forEach(roleMenu -> {
                menuIds.add(roleMenu.getMenuId() + "");
                menuNames.add(roleMenu.getMenu().getName());
            });

            List<SelectGroupOption> roleRegion = elementUiService.fetchRoleRegion(role.getId());

            roleTableModel.setRoleRegions(roleRegion);
            roleTableModel.setMenuIds(menuIds);
            roleTableModel.setMenuNames(menuNames);

            roleTableModels.add(roleTableModel);
        });

        return roleTableModels;
    }

    public String editRole(RoleModel roleModel) {
        Optional<Role> roleOptional = roleRepository.findByName(roleModel.getName());
        if (roleOptional.isPresent() && !roleOptional.get().getId().equals(roleModel.getId())) {
            return ResponseConstants.EXISTS;
        }

        roleOptional = roleRepository.findById(roleModel.getId());
        if (!roleOptional.isPresent()) {
            return ResponseConstants.FAILURE;
        }

        Role role = roleOptional.get();
        role.setName(roleModel.getName());

        role = roleRepository.save(role);

        roleMenuRepository.deleteByRoleId(role.getId());

        List<RoleMenu> roleMenus = new ArrayList<>();
        for (String menu : roleModel.getMenus()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(Integer.parseInt(menu));
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        }

        roleMenuRepository.saveAll(roleMenus);

        rolePriviledgeRepository.deleteByRoleId(role.getId());

        List<RolePriviledge> rolePriviledges = new ArrayList<>();
        for (String  regionId: roleModel.getRolePrividges()){
            RolePriviledge rolePriviledge = new RolePriviledge();
            rolePriviledge.setRoleId(role.getId());
            rolePriviledge.setMetaRegionId(regionId);
            rolePriviledges.add(rolePriviledge);
        }

        rolePriviledgeRepository.saveAll(rolePriviledges);

        return ResponseConstants.SUCCESS;
    }

    /**
     * 删除角色
     *
     * @param id roleid
     * @return 是否成
     */
    public String deleteRole(Integer id) {
        List<User> users = userRepository.findByRoleId(id);
        if (!CollectionUtils.isEmpty(users)) {
            return ResponseConstants.IN_USE;
        }

        roleRepository.deleteById(id);
        roleMenuRepository.deleteByRoleId(id);
        rolePriviledgeRepository.deleteByRoleId(id);

        return ResponseConstants.SUCCESS;
    }

    /**
     * 组合地市下拉列表，以省份分组
     *
     * @return 地市下拉列表
     */
    public List<SelectGroupOption> loadAllMetaRegion() {

        List<MetaRegion> metaRegions = metaRegionRepository.findByLevel("1");
        if (CollectionUtils.isEmpty(metaRegions)){
            return null;
        }
        List<SelectGroupOption> groupOptions = convertMetaRegion(metaRegions);
        return groupOptions;
    }

    /**
     * 根据roleId获取该角色的所有RoleRegion
     * @param roleId
     * @return
     */
    public List<String> getRolePriviledge(Integer roleId) {

        List<RolePriviledge> rolePriviledges = rolePriviledgeRepository.findByRoleId(roleId);
        if (rolePriviledges == null){
            return null;
        }
        List<String> priviledge = new ArrayList<>();
        rolePriviledges.forEach(rolePriviledge->{
            priviledge.add(rolePriviledge.getMetaRegionId());
        });
        return priviledge;
    }

    private List<SelectGroupOption> convertMetaRegion(List<MetaRegion> metaRegions) {

        List<SelectGroupOption> selectGroupOptions = new ArrayList<>();

        metaRegions.forEach(metaRegion -> {
            SelectGroupOption selectGroupOption = new SelectGroupOption();
            List<SelectGroupOption> children = new ArrayList<>();
            selectGroupOption.setLabel(metaRegion.getName());
            selectGroupOption.setValue(metaRegion.getId());
            selectGroupOption.setExtension(metaRegion.getLevel());
            if (!CollectionUtils.isEmpty(metaRegion.getChildren())) {
                children = convertMetaRegion(metaRegion.getChildren());
            }
            selectGroupOption.setChildren(children);
            selectGroupOptions.add(selectGroupOption);
        });
        return selectGroupOptions;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setRoleMenuRepository(RoleMenuRepository roleMenuRepository) {
        this.roleMenuRepository = roleMenuRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMetaRegionRepository(MetaRegionRepository metaRegionRepository) {
        this.metaRegionRepository = metaRegionRepository;
    }

    @Autowired
    public void setRolePriviledgeRepository(RolePriviledgeRepository rolePriviledgeRepository) {
        this.rolePriviledgeRepository = rolePriviledgeRepository;
    }

    @Autowired
    public void setElementUiService(ElementUiService elementUiService) {
        this.elementUiService = elementUiService;
    }
}
