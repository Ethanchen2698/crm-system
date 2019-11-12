package com.ethan.crmsystem.web;

import com.ethan.crmsystem.domain.MetaRegion;
import com.ethan.crmsystem.service.MetaService;
import com.ethan.crmsystem.web.model.MetaRegionModel;
import com.ethan.crmsystem.web.model.NesTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: MetaController.java, 2018/7/31 15:54 $
 */
@RestController
@RequestMapping("/meta")
public class MetaController {

    private MetaService metaService;

    @PostMapping("/addMetaRegion")
    public String addMetaRegion(@Validated MetaRegionModel metaRegionModel) {
        return metaService.addMetaRegion(metaRegionModel);
    }

    @DeleteMapping("/deleteMetaRegion/{id}")
    public void deleteMetaRegion(@PathVariable String id) {
        metaService.deleteMetaRegion(id);
    }

    @GetMapping("/findRegionNodeById/{id}")
    public Optional<MetaRegion> findRegionNodeById(@PathVariable String id) {
        return metaService.findRegionNodeById(id);
    }

    @PostMapping("/updateRegionNode")
    public String updateRegionNode(@Validated MetaRegionModel metaRegionModel) {
        return metaService.updateRegionNode(metaRegionModel);
    }

    /**
     * 行政区树形结构数据
     */
    @GetMapping("/fetchRegionNesTable")
    public List<NesTableModel> fetchRegionNesTable() {
        return metaService.fetchRegionNesTable();
    }

    @Autowired
    public void setMetaService(MetaService metaService) {
        this.metaService = metaService;
    }
}
