package org.egreenbriar.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Component
public class HomeController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) throws FileNotFoundException, IOException {
        return "home";
    }

}
