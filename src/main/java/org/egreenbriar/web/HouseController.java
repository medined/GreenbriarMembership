package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.model.House;
import org.egreenbriar.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class HouseController {

    @Autowired
    private DatabaseService databaseService = null;
    
    @RequestMapping("/house/toggle_2014_membership/{houseUuid}")
    @ResponseBody
    public void toggle2014Membership(Model model, @PathVariable String houseUuid) throws FileNotFoundException, IOException {
        House house = databaseService.getHouse(houseUuid);
        house.toggle2014Membership();
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

}
