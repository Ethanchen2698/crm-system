package com.ethan.crmsystem.web;

import com.ethan.crmsystem.service.ReturnVisitService;
import com.ethan.crmsystem.web.model.ReturnVisitForm;
import com.ethan.crmsystem.web.model.ReturnVisitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @version : ReturnVisitController.java,2019/11/14 19:29 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@RestController
@RequestMapping("/returnVisit")
public class ReturnVisitController {

    private ReturnVisitService returnVisitService;

    @PostMapping("/findReturnVisitLog")
    public List<ReturnVisitModel> findReturnVisitLog(@Validated ReturnVisitForm returnVisitForm){
        return returnVisitService.findEquipmentTable(returnVisitForm);
    }

    @PostMapping("/countReturnVisitLog")
    public String countReturnVisit(@Validated ReturnVisitForm returnVisitForm){
        return returnVisitService.countReturnVisit(returnVisitForm);
    }

    @PostMapping("/addReturnVisitLog")
    public String addReturnVisitLog(@Validated ReturnVisitModel returnVisitModel){
        return returnVisitService.addReturnVisitLog(returnVisitModel);
    }

    @PostMapping("/updateReturnVisitLog")
    public String updateReturnVisitLog(@Validated ReturnVisitModel returnVisitModel){
        return returnVisitService.updateReturnVisitLog(returnVisitModel);
    }

    @DeleteMapping("/deleteReturnVisitLog/{id}")
    public String deleteAfterSalesLog(@PathVariable String id){
        return returnVisitService.deleteReturnVisitLog(id);
    }

    @GetMapping("/findReturnVisitByCustomerCode/{customerCode}")
    public List<ReturnVisitModel> findReturnVisitByCustomerCode(@PathVariable String customerCode){
        return returnVisitService.findReturnVisitByCustomerCode(customerCode);
    }

    @Autowired
    public void setReturnVisitService(ReturnVisitService returnVisitService) {
        this.returnVisitService = returnVisitService;
    }
}
