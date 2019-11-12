package com.ethan.crmsystem.web;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.domain.User;
import com.ethan.crmsystem.service.ElementUiService;
import com.ethan.crmsystem.web.model.NesTableModel;
import com.ethan.crmsystem.web.model.SelectGroupOption;
import com.ethan.crmsystem.web.model.SelectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 *
 * @version : UiController.java,2019/11/6 23:58 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@RestController
@RequestMapping("/ui")
public class UiController {

    private ElementUiService elementUiService;

    private RequestContext requestContext;

    @GetMapping("/loadMenuList")
    public List<SelectOption> loadMenuList() {
        return elementUiService.loadMenuList();
    }

    @GetMapping("/fetchRoleOption")
    public List<SelectOption> fetchRoleOption() {
        return elementUiService.fetchRoleOption();
    }

    @GetMapping("/fetchUserOptions")
    public List<SelectOption> fetchUserOptions() {
        return elementUiService.fetchUserOptions();
    }

    @GetMapping("/loadUsePriviledge")
    public List<SelectGroupOption> loadUsePriviledge() {
        User user = requestContext.getRequestUser();
        return elementUiService.fetchRoleRegion(user.getRoleId());
    }

    @GetMapping("/findRegionById/{id}")
    public SelectOption findRegionById(@PathVariable String id) {
        return elementUiService.findRegionById(id);
    }

    @Autowired
    public void setElementUiService(ElementUiService elementUiService) {
        this.elementUiService = elementUiService;
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }
}
