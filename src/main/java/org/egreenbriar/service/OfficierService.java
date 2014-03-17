package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public void read() throws FileNotFoundException, IOException {
        String[] components = null;
        
        CSVReader cvsReader = new CSVReader(new FileReader(getOfficierFile()));
        
        while ((components = cvsReader.readNext()) != null) {
            String title = components[0];
            String name = components[1];
            getOfficiers().put(title, name);
        }

    }

    public String get(String title) {
        return officiers.get(title);
    }

    /**
     * @return the officierFile
     */
    public String getOfficierFile() {
        return officierFile;
    }

    /**
     * @param officierFile the officierFile to set
     */
    public void setOfficierFile(String officierFile) {
        this.officierFile = officierFile;
    }

    /**
     * @return the officiers
     */
    public Map<String, String> getOfficiers() {
        return officiers;
    }

    /**
     * @param officiers the officiers to set
     */
    public void setOfficiers(Map<String, String> officiers) {
        this.officiers = officiers;
    }

}
