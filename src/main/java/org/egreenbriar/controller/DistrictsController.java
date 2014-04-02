package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.DistrictService;
import org.egreenbriar.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class DistrictsController {

    @Autowired
    private DistrictService districtService = null;

    @Autowired
    private BreadcrumbService breadcrumbService = null;
    
    @Autowired
    private HouseService houseService = null;

    @RequestMapping("/districts")
    public String communityHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("districtService", districtService);
        model.addAttribute("districts", districtService.getDistricts());
        model.addAttribute("houses", houseService.getHouses());
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "districts";
    }

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public BreadcrumbService getBreadcrumbService() {
        return breadcrumbService;
    }

}
