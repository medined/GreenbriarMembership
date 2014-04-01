package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
public class SteetController {

    @Autowired
    private StreetService streetService = null;
    
    @RequestMapping("/streets")
    public String communityHandler(Model model) throws FileNotFoundException, IOException {
        model.addAttribute("streets", streetService.getStreets());
        return "streets";
    }

    /**
     * @param streetReader the streetReader to set
     */
    public void setStreetReader(StreetService streetReader) {
        this.streetService = streetReader;
    }
}
