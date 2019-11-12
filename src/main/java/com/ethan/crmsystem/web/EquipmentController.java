package com.ethan.crmsystem.web;

import com.ethan.crmsystem.service.EquipmentService;
import com.ethan.crmsystem.web.model.EquipmentForm;
import com.ethan.crmsystem.web.model.EquipmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @version : EquipmentController.java,2019/11/12 19:55 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private EquipmentService equipmentService;

    @PostMapping("/findEquipmentTable")
    public List<EquipmentModel> findEquipmentTable(@Validated EquipmentForm equipmentForm){
        return equipmentService.findCustomerTable(equipmentForm);
    }

    @PostMapping("/countEquipment")
    public String countEquipment(@Validated EquipmentForm equipmentForm){
        return equipmentService.countEquipment(equipmentForm);
    }

    @PostMapping("/addEquipment")
    public String addEquipment(@Validated EquipmentModel equipmentModel){
        return equipmentService.addEquipment(equipmentModel);
    }

    @PostMapping("/updateEquipment")
    public String updateEquipment(@Validated EquipmentModel equipmentModel){
        return equipmentService.updateEquipment(equipmentModel);
    }

    @DeleteMapping("/deleteEquipment/{id}")
    public String deleteEquipment(@PathVariable String id){
        return equipmentService.deleteEquipment(id);
    }

    @Autowired
    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
}
