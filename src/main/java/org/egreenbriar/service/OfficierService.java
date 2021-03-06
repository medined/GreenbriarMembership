package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OfficierService {
    
    @Value("${officiers.csv.file}")
    private String officierFile = null;

    private Map<String, String> officiers = new TreeMap<>();

    @PostConstruct
    public void initialize() {
        try {
            String[] components = null;
            
            CSVReader cvsReader = new CSVReader(new FileReader(officierFile));
            
            while ((components = cvsReader.readNext()) != null) {
                String title = components[0];
                String name = components[1];
                officiers.put(title, name);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void write() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(officierFile)) {
            writer.println("Title,Person");
            for (Map.Entry<String, String> entry : officiers.entrySet()) {
                writer.printf("%s,%s\n", entry.getKey(), entry.getValue());
            }
        }
    }
    
    public void updateDistrictRepresentative(final String districtName, final String representativeName) {
        officiers.put(districtName, representativeName);
    }

    public String getDistrictRepresentative(final String districtName) {
        return officiers.get(districtName);
    }

    public void setOfficierFile(String officierFile) {
        this.officierFile = officierFile;
    }

    public void setOfficiers(Map<String, String> officiers) {
        this.officiers = officiers;
    }

}
