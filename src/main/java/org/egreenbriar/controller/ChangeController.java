package org.egreenbriar.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import org.egreenbriar.service.BreadcrumbService;
import org.egreenbriar.service.ChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class ChangeController {

    @Autowired
    private BreadcrumbService breadcrumbService = null;
    
    @Autowired
    private ChangeService changeService = null;
    
    @RequestMapping("/changes")
    public String changes(Model model) throws IOException {
        Set<String> changes = new TreeSet<>(Collections.reverseOrder());
        
        model.addAttribute("changeService", changeService);

        for (String change : changeService.getChanges()) {
            changes.add(change);
        }
        model.addAttribute("changes", changes);
        
        breadcrumbService.clear();
        breadcrumbService.put("Home", "/home");
        breadcrumbService.put("Logout", "/j_spring_security_logout");        
        model.addAttribute("breadcrumbs", breadcrumbService.getBreadcrumbs());

        return "changes";
    }
    
    public BreadcrumbService getBreadcrumbService() {
        return breadcrumbService;
    }

    public void setBreadcrumbService(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

}
