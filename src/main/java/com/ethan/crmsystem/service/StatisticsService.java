package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.infra.domain.MetaRegion;
import com.ethan.crmsystem.infra.domain.User;
import com.ethan.crmsystem.infra.mapper.CustomerMapper;
import com.ethan.crmsystem.infra.mapper.EquipmentMapper;
import com.ethan.crmsystem.infra.repository.MetaRegionRepository;
import com.ethan.crmsystem.web.model.SelectGroupOption;
import com.ethan.crmsystem.web.model.StatisticsForm;
import com.ethan.crmsystem.web.model.StatisticsModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:ethanchen2698@163.com">ethan chen</a>
 * @version : StatisticsService.java, 2019/12/4 22:30 $
 */
@Service
@Slf4j
public class StatisticsService {

    private MetaRegionRepository metaRegionRepository;

    private ElementUiService elementUiService;

    private RequestContext requestContext;

    private CustomerMapper customerMapper;

    private EquipmentMapper equipmentMapper;

    public List<SelectGroupOption> loadCity(String value) {

        User user = requestContext.getRequestUser();
        List<SelectGroupOption> citys = new ArrayList<>();

        List<SelectGroupOption> metaRegions = elementUiService.fetchRoleRegion(user.getRoleId());
        if (CollectionUtils.isEmpty(metaRegions)){
            return null;
        }
        metaRegions.forEach(metaRegion -> {
            SelectGroupOption selectGroupOption = new SelectGroupOption();

            selectGroupOption.setValue(metaRegion.getValue());
            selectGroupOption.setLabel(metaRegion.getLabel());
            selectGroupOption.setExtension(metaRegion.getExtension());
            citys.add(selectGroupOption);
        });

        if (!StringUtils.equals("",value) && !StringUtils.equals("undefined",value)){
            Optional<MetaRegion> optional = metaRegionRepository.findById(value);
            citys.forEach(item ->{
                if (StringUtils.equals(item.getValue(),optional.get().getParent())){
                    List<SelectGroupOption> children = new ArrayList<>();
                    SelectGroupOption selectGroupOption = new SelectGroupOption();
                    selectGroupOption.setValue(optional.get().getId());
                    selectGroupOption.setLabel(optional.get().getName());
                    selectGroupOption.setExtension(optional.get().getLevel());
                    children.add(selectGroupOption);
                    item.setChildren(children);
                }

            });
        }

        return citys;
    }

//    public List<StatisticsModel> queryStatisticsData(StatisticsForm statisticsForm) {
//
//        if (StringUtils.isEmpty(statisticsForm.getRegion()) || StringUtils.isEmpty(statisticsForm.getViewLevel())){
//            return null;
//        }
//
//        User user = requestContext.getRequestUser();
//        List<StatisticsModel> customerStatistics = customerMapper.findStatisticsData(statisticsForm,user.getRoleId());
//        List<StatisticsModel> equipmentStatistics = equipmentMapper.findStatisticsData(statisticsForm,user.getRoleId());
//        customerStatistics.forEach(item ->{
//            equipmentStatistics.forEach(it ->{
//                if (StringUtils.equals(item.getRegion(),it.getRegion())){
//                    item.setEquipmentCnt(it.getEquipmentCnt());
//                }
//            });
//        });
//        return customerStatistics;
//    }

    public List<StatisticsModel> queryStatisticsData(StatisticsForm statisticsForm) {

        if (StringUtils.isEmpty(statisticsForm.getRegion()) || StringUtils.isEmpty(statisticsForm.getViewLevel())){
            return null;
        }

        User user = requestContext.getRequestUser();
        List<StatisticsModel> statisticalData = new ArrayList<>();

        List<SelectGroupOption> metaRegions = elementUiService.fetchRoleRegion(user.getRoleId());
        if (CollectionUtils.isEmpty(metaRegions)){
            return null;
        }
        List<SelectGroupOption> citys = new ArrayList<>();
        metaRegions.forEach(mr ->{
            if (StringUtils.equals(statisticsForm.getViewLevel(),"1")){
                if (StringUtils.equals(mr.getValue(),statisticsForm.getRegion())){
                    citys.addAll(mr.getChildren());
                }
            }else if(StringUtils.equals(statisticsForm.getViewLevel(),"2")){
                mr.getChildren().forEach(item ->{
                    if (StringUtils.equals(item.getValue(),statisticsForm.getRegion())){
                        citys.addAll(item.getChildren());
                    }
                });
            }
        });
        citys.forEach(city->{
            StatisticsModel statisticsModel = new StatisticsModel();
            statisticsModel.setName(city.getLabel());
            statisticsModel.setRegion(city.getValue());
            statisticsModel.setLevel(city.getExtension());
            String cusCnt = customerMapper.findStatisticsData(city.getValue(),statisticsForm.getViewLevel());
            statisticsModel.setCustomerCnt(cusCnt);
            String equCnt = equipmentMapper.findStatisticsData(city.getValue(),statisticsForm.getViewLevel());
            statisticsModel.setEquipmentCnt(equCnt);

            statisticalData.add(statisticsModel);
        });
        return statisticalData;
    }

    @Autowired
    public void setMetaRegionRepository(MetaRegionRepository metaRegionRepository) {
        this.metaRegionRepository = metaRegionRepository;
    }

    @Autowired
    public void setElementUiService(ElementUiService elementUiService) {
        this.elementUiService = elementUiService;
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Autowired
    public void setEquipmentMapper(EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
    }
}
