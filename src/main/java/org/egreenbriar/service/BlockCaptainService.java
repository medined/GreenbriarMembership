package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlockCaptainService {

    private final Map<String, String> captains = new TreeMap<>();

    @Value("${blockcaptain.csv.file}")
    private String captainFile = null;

    @PostConstruct
    public void read() throws FileNotFoundException, IOException {
        String[] components = null;
        
        CSVReader cvsReader = new CSVReader(new FileReader(captainFile));
        
        while ((components = cvsReader.readNext()) != null) {
            String blockName = components[0];
            String lastName = components[1];
            String firstName = components[2];
            getCaptains().put(blockName, firstName + " " + lastName);
        }

    }

    public String getCaptainFile() {
        return captainFile;
    }

    public void setCaptainFile(String captainFile) {
        this.captainFile = captainFile;
    }

    /**
     * @return the captains
     */
    public Map<String, String> getCaptains() {
        return captains;
    }

}
