package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
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
    public void read() {
        String[] components = null;
        
        CSVReader cvsReader = null;
        try {
            cvsReader = new CSVReader(new FileReader(captainFile));
            while ((components = cvsReader.readNext()) != null) {
                String blockName = components[0];
                String captainName = components[1];
                captains.put(blockName, captainName);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (cvsReader != null) {
                try {
                    cvsReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        

    }
    
    public Set<String> getBlocksWithoutCaptains() {
        Set<String> blocksWithoutCaptain = new TreeSet<>();
        for (Map.Entry<String, String> entry : captains.entrySet()) {
            String blockName = entry.getKey();
            String captainName = entry.getValue().trim();
            if (captainName.isEmpty()) {
                blocksWithoutCaptain.add(blockName);
            }
        }
        return blocksWithoutCaptain;
    }

    private synchronized void write() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(captainFile)) {
            for (Map.Entry<String, String> entry : captains.entrySet()) {
                writer.printf("%s,%s\n", entry.getKey(), entry.getValue());
            }
        }
    }

    public String getCaptainFile() {
        return captainFile;
    }

    public void setCaptainFile(String captainFile) {
        this.captainFile = captainFile;
    }

    public Map<String, String> getCaptains() {
        return captains;
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

    public void update(final String blockName, final String captainName) throws FileNotFoundException {
        captains.put(blockName, captainName);
        write();
    }

    public String getCaptainName(String blockName) {
        return captains.get(blockName);
    }

}
