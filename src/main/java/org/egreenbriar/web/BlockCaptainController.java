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
public class BlockCaptainController {

    @Autowired
    private DatabaseService databaseService = null;
    
    @Autowired
    private BreadcrumbService breadcrumbService = null;
    
    @RequestMapping("/blockcaptains")
    public String listHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("blocks", databaseService.getBlocks());

        breadcrumbService.clear();
        breadcrumbService.put("Home", "/");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());
        
        return "blockcaptains";
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

}
