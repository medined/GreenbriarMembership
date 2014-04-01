package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlockCaptainService {

    private final Map<String, String> captains = new TreeMap<>();

    @Value("${blockcaptain.csv.file}")
    private String captainFile = null;
    
    @Autowired
    private ChangeService changeService = null;

    @PostConstruct
    public void read() throws FileNotFoundException, IOException {
        String[] components = null;
        
        CSVReader cvsReader = new CSVReader(new FileReader(captainFile));
        
        while ((components = cvsReader.readNext()) != null) {
            String blockName = components[0];
            String captainName = components[1];
            getCaptains().put(blockName, captainName);
        }

    }
    
    public synchronized void write(final Map<String, Block> blocks) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(captainFile)) {
            for (Map.Entry<String, Block> entry : blocks.entrySet()) {
                writer.printf("%s,%s\n", entry.getKey(), entry.getValue().getCaptainName());
            }
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

    /**
     * @param changeService the changeService to set
     */
    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

}
