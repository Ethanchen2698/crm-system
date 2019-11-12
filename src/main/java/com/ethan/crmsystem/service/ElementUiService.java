package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.domain.*;
import com.ethan.crmsystem.exception.AuthenticationException;
import com.ethan.crmsystem.repository.*;
import com.ethan.crmsystem.web.model.NesTableModel;
import com.ethan.crmsystem.web.model.SelectGroupOption;
import com.ethan.crmsystem.web.model.SelectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: ElementUiService.java, 2018/8/6 16:10 $
 */
@Service
public class ElementUiService {

    private MenuRepository menuRepository;

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private MetaRegionRepository metaRegionRepository;

    private RoleService roleService;

    private RolePriviledgeRepository rolePriviledgeRepository;

    public List<SelectOption> loadMenuList() {
        List<Menu> menus = menuRepository.findAll();

        List<SelectOption> selectOptions = new ArrayList<>();

        menus.forEach(menu -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(menu.getName());
            selectOption.setValue(menu.getId() + "");
            selectOptions.add(selectOption);
        });

        return selectOptions;
    }

    public List<SelectOption> fetchRoleOption() {
        List<Role> roles = roleRepository.findAll();

        List<SelectOption> selectOptions = new ArrayList<>();

        roles.forEach(role -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(role.getName());
            selectOption.setValue(role.getId() + "");
            selectOptions.add(selectOption);
        });

        return selectOptions;
    }

    public List<SelectOption> fetchUserOptions() {
        List<User> users = userRepository.findAll();

        List<SelectOption> selectOptions = new ArrayList<>();

        users.forEach(user -> {
            SelectOption selectOption = new SelectOption();
            selectOption.setLabel(user.getFullName());
            selectOption.setValue(user.getId());
            selectOptions.add(selectOption);
        });

        return selectOptions;
    }



    /**
     * 根据roleId生成当前角色的行政区树形结构数据
     * @return
     */
    public List<SelectGroupOption> fetchRoleRegion(Integer roleId){

        List<SelectGroupOption> metaRegions = roleService.loadAllMetaRegion();

        List<SelectGroupOption> roleRegion = convertRoleRegion(metaRegions,roleId);

        return roleRegion;
    }

    /**
     * 根据regionid
     * @param id
     * @return
     */
    public SelectOption findRegionById(String id) {

        Optional<MetaRegion>  metaRegion = metaRegionRepository.findById(id);
        SelectOption selectOption = new SelectOption();
        selectOption.setLabel(metaRegion.get().getName());
        selectOption.setValue(metaRegion.get().getId());
        selectOption.setExtension(metaRegion.get().getCode());

        return selectOption;
    }

    private List<SelectGroupOption> convertRoleRegion(List<SelectGroupOption> metaRegions,Integer roleId){

        //省
        Iterator<SelectGroupOption> pronice = metaRegions.iterator();
        while (pronice.hasNext()){
            List<SelectGroupOption> regionChildren = pronice.next().getChildren();
            //市
            Iterator<SelectGroupOption> city = regionChildren.iterator();
            while (city.hasNext()){
                List<SelectGroupOption> children = city.next().getChildren();
                //县
                Iterator<SelectGroupOption> county = children.iterator();
                while (county.hasNext()){
                    SelectGroupOption region = county.next();
                    RolePriviledge priviledge =rolePriviledgeRepository.findByMetaRegionIdAndRoleId(region.getValue(),roleId);
                    if (priviledge == null){
                        county.remove();
                    }
                }
                if (CollectionUtils.isEmpty(children)){
                    city.remove();
                }
            }
            if (CollectionUtils.isEmpty(regionChildren)){
                pronice.remove();
            }
        }
        return metaRegions;
    }

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
