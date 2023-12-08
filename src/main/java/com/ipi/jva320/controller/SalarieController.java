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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/salaries")
public class SalarieController {
    @Autowired
    SalarieAideADomicileService salarieAideADomicileService;
    @Autowired
    private CommonController commonController;

    @GetMapping("/{id}")
    public String detailSalarie(ModelMap modelMap, @PathVariable long id){
        modelMap.addAllAttributes(commonController.getCommonValue());
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
    public String listSalaries(ModelMap modelMap, @RequestParam(name = "nom", required = false) String nom){
        modelMap.addAllAttributes(commonController.getCommonValue());
        List<SalarieAideADomicile> salarieAideADomicileList;

        if(nom!=null){
            salarieAideADomicileList = salarieAideADomicileService.getSalaries().stream().filter(salarieAideADomicile -> salarieAideADomicile.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
        }else {
            salarieAideADomicileList = salarieAideADomicileService.getSalaries();
        }

        modelMap.put("salaries", salarieAideADomicileList);
        return "list";
    }


    @GetMapping("/aide/new")
    public String createSalarie(ModelMap modelMap){
        modelMap.addAllAttributes(commonController.getCommonValue());
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
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Le salarié n'as pas été ajouté.");
        }
    }
    @GetMapping("/{id}/delete")
    public String deleteSalarie(@PathVariable long id){
        try {
            salarieAideADomicileService.deleteSalarieAideADomicile(id);
            return "redirect:/salaries";
        } catch (SalarieException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'id ne correspond à aucun salarié");
        }
    }


}
