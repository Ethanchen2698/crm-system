package com.ethan.crmsystem.web;

import com.ethan.crmsystem.service.StatisticsService;
import com.ethan.crmsystem.web.model.SelectGroupOption;
import com.ethan.crmsystem.web.model.StatisticsForm;
import com.ethan.crmsystem.web.model.StatisticsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @author <a href="mailto:ethanchen2698@163.com">ethan chen</a>
 * @version : StatisticsController.java, 2019/12/4 22:26 $
 */
@RestController
@RequestMapping("/statistical")
public class StatisticsController {

    private StatisticsService statisticsService;

    @GetMapping("/loadCity")
    public List<SelectGroupOption> loadCity(@Validated String value){

        return statisticsService.loadCity(value);
    }

    @PostMapping("/queryStatisticsData")
    public List<StatisticsModel> queryStatisticsData(@Validated StatisticsForm statisticsForm){

        return statisticsService.queryStatisticsData(statisticsForm);
    }

    @Autowired
    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
}
