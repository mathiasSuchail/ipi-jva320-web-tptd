package com.ipi.jva320.controller;


import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
public class CommonController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;
    public ModelMap getCommonValue(){
       ModelMap modelMap = new ModelMap();

       modelMap.put("countSalaries", salarieAideADomicileService.countSalaries());

       return modelMap;
    }
}