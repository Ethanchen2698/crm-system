package com.ethan.crmsystem.web;

import com.ethan.crmsystem.service.AfterSalesService;
import com.ethan.crmsystem.web.model.AfterSalesForm;
import com.ethan.crmsystem.web.model.AfterSalesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @version : AfterSalesLogController.java,2019/11/13 22:11 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@RestController
@RequestMapping("/afterSales")
public class AfterSalesController {

    private AfterSalesService afterSalesService;

    @PostMapping("/findAfterSaleLog")
    public List<AfterSalesModel> findAfterSalesTable(@Validated AfterSalesForm afterSalesForm){
        return afterSalesService.findEquipmentTable(afterSalesForm);
    }

    @PostMapping("/countAfterSalesLog")
    public String countAfterSales(@Validated AfterSalesForm afterSalesForm){
        return afterSalesService.countAfterSales(afterSalesForm);
    }

    @PostMapping("/addAfterSaleLog")
    public String addAfterSalesLog(@Validated AfterSalesModel afterSalesModel){
        return afterSalesService.addAfterSalesLog(afterSalesModel);
    }

    @PostMapping("/updateAfterSaleLog")
    public String updateAfterSalesLog(@Validated AfterSalesModel afterSalesModel){
        return afterSalesService.updateAfterSalesLog(afterSalesModel);
    }

    @DeleteMapping("/deleteAfterSaleLog/{id}")
    public String deleteAfterSalesLog(@PathVariable String id){
        return afterSalesService.deleteAfterSalesLog(id);
    }

    @Autowired
    public void setAfterSalesService(AfterSalesService afterSalesService) {
        this.afterSalesService = afterSalesService;
    }
}
