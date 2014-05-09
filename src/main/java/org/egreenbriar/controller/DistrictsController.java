package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.DistrictService;
import org.egreenbriar.service.OfficierService;
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
    private OfficierService officierService = null;

    @Autowired
    private BreadcrumbService breadcrumbService = null;
    
    @RequestMapping("/districts")
    public String communityHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("districtService", districtService);
        model.addAttribute("officierService", officierService);
        model.addAttribute("districts", districtService.getDistricts());
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "districts";
    }

    public BreadcrumbService getBreadcrumbService() {
        return breadcrumbService;
    }

    public void setOfficierService(OfficierService officierService) {
        this.officierService = officierService;
    }

    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

}
