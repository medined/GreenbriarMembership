package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StreetReader {

    @Value("${streets.csv.file}")
    private String streetFile = null;

    private final Set<String> streets = new TreeSet<>();

    public void read() throws FileNotFoundException, IOException {
        String[] components = null;
        
        CSVReader streetReader = new CSVReader(new FileReader(getStreetFile()));
        while ((components = streetReader.readNext()) != null) {
            String streetName = components[1];
            getStreets().add(streetName);
        }

    }

    /**
     * @return the streets
     */
    public Set<String> getStreets() {
        return streets;
    }

    /**
     * @return the streetFile
     */
    public String getStreetFile() {
        return streetFile;
    }

    /**
     * @param streetFile the streetFile to set
     */
    public void setStreetFile(String streetFile) {
        this.streetFile = streetFile;
    }
}
