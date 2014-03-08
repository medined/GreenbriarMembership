package org.egreenbriar.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.egreenbriar.service.StreetReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class CommunityController {

    @Value("${membership.csv.file}")
    String membershipFile = null;

    @Value("${streets.csv.file}")
    String streetFile = null;

    @Value("${blockcaptain.csv.file}")
    String captainFile = null;

    @Autowired
    private StreetReader streetReader = null;
    
    @RequestMapping("/community")
    @ResponseBody
    public String communityHandler() throws FileNotFoundException, IOException {
        return streetReader.getStreetFile();
    }

    /**
     * @return the streetReader
     */
    public StreetReader getStreetReader() {
        return streetReader;
    }

    /**
     * @param streetReader the streetReader to set
     */
    public void setStreetReader(StreetReader streetReader) {
        this.streetReader = streetReader;
    }
}
