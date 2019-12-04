package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.infra.domain.MetaRegion;
import com.ethan.crmsystem.infra.repository.MetaRegionRepository;
import com.ethan.crmsystem.web.model.MetaRegionModel;
import com.ethan.crmsystem.web.model.NesTableModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description:元数据增删改查服务
 * TODO 所有删除都需要判断是否被使用
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: MetaService.java, 2018/7/30 14:25 $
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class MetaService {

    private MetaRegionRepository metaRegionRepository;

    /**
     * 增加行政区划
     *
     * @param metaRegionModel form表单参数
     */
    public String addMetaRegion(MetaRegionModel metaRegionModel) {
        Integer count = metaRegionRepository.countByCode(metaRegionModel.getCode());
        if (count != 0) {
            return ResponseConstants.EXISTS;
        }

        MetaRegion metaRegion = new MetaRegion();
        metaRegion.setCode(metaRegionModel.getCode());
        metaRegion.setName(metaRegionModel.getName());
        metaRegion.setParent(metaRegionModel.getParent());
        metaRegion.setLevel(metaRegionModel.getLevel());

        metaRegionRepository.save(metaRegion);

        return ResponseConstants.SUCCESS;
    }

    /**
     * 删除行政区划
     *
     * @param id uuid
     */
    public void deleteMetaRegion(String id) {
        metaRegionRepository.deleteById(id);
    }

    /**
     * 查找行政区划
     *
     * @param id uuid
     */
    public Optional<MetaRegion> findRegionNodeById(String id) {
        return metaRegionRepository.findById(id);
    }

    /**
     * 根据ID修改行政区划
     */
    public String updateRegionNode(MetaRegionModel metaRegionModel) {
        Optional<MetaRegion> existMetaRegion = metaRegionRepository.findByCode(metaRegionModel.getCode());
        if (existMetaRegion.isPresent()) {
            if (!existMetaRegion.get().getId().equals(metaRegionModel.getId())) {
                return ResponseConstants.EXISTS;
            }
        }

        Optional<MetaRegion> metaRegionOptional = metaRegionRepository.findById(metaRegionModel.getId());
        if (metaRegionOptional.isPresent()) {
            MetaRegion metaRegion = metaRegionOptional.get();
            metaRegion.setCode(metaRegionModel.getCode());
            metaRegion.setName(metaRegionModel.getName());

            metaRegionRepository.save(metaRegion);
        }

        return ResponseConstants.SUCCESS;
    }

    /**
     * 组合行政区树形结构数据
     * 行政区最高3层 分别为  省  市  区
     */
    public List<NesTableModel> fetchRegionNesTable() {
        List<MetaRegion> metaRegions = metaRegionRepository.findByLevel("1");

        return convertMetaRegion(metaRegions);
    }

    private List<NesTableModel> convertMetaRegion(List<MetaRegion> metaRegions) {
        List<NesTableModel> nesTableModels = new ArrayList<>();
        metaRegions.forEach(metaRegion -> {
            NesTableModel nesTableModel = new NesTableModel();
            nesTableModel.setLeftOption(metaRegion.getName());
            nesTableModel.setValue(metaRegion.getId());
            nesTableModel.setLevel(metaRegion.getLevel());
            nesTableModel.setShowEdit(true);
            nesTableModel.setShowPlus(true);
            nesTableModel.setShowTrash(true);
            if (!CollectionUtils.isEmpty(metaRegion.getChildren())) {
                List<NesTableModel> children = convertMetaRegion(metaRegion.getChildren());
                nesTableModel.setChildren(children);
            }
            nesTableModels.add(nesTableModel);
        });

        return nesTableModels;
    }

    @Autowired
    public void setMetaRegionRepository(MetaRegionRepository metaRegionRepository) {
        this.metaRegionRepository = metaRegionRepository;
    }
}
