package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class DistrictsController {

    @Autowired
    private DatabaseService databaseService = null;
    
    @Autowired
    private BreadcrumbService breadcrumbService = null;

    @RequestMapping("/districts")
    public String communityHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("community", databaseService.getCommunity());
        model.addAttribute("districts", databaseService.getDistricts());
        model.addAttribute("houses", databaseService.getHouses());
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "districts";
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }
}
