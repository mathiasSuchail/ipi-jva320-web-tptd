package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;

@Controller
@RequestMapping("/salaries")
public class SalarieController {
    @Autowired
    SalarieAideADomicileService salarieAideADomicileService;
    @GetMapping("/{id}")
    public String detailSalarie(ModelMap modelMap, @PathVariable long id){
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        modelMap.put("salarie", salarie);
        modelMap.put("actionUrl", "/salaries/".concat((Long.toString(id))));
        return "detail_Salarie";
    }
    @PostMapping("/{id}")
    public String updateSalarie(SalarieAideADomicile salarie){
        try{
            salarieAideADomicileService.updateSalarieAideADomicile(salarie);
            return "redirect:/salaries";
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Le salarié n'as pas été mis a jour.");//ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Echec de création du salarié : "+e.getMessage());
        }
    }

    @GetMapping("")
    public String listSalaries(ModelMap modelMap){
        modelMap.put("salaries", salarieAideADomicileService.getSalaries());
        return "list";
    }

    @GetMapping("/aide/new")
    public String createSalarie(ModelMap modelMap){
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        modelMap.put("salarie", salarie);
        modelMap.put("actionUrl", "/salaries/save");
        return "detail_Salarie";
    }
    @PostMapping("/save")
    public String createSalarie(SalarieAideADomicile salarie){
        try{
            salarieAideADomicileService.creerSalarieAideADomicile(salarie);
            return "redirect:/salaries";
        }
            catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Le salarié n'as pas été ajouté.");//ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Echec de création du salarié : "+e.getMessage());
        }
    }

}
