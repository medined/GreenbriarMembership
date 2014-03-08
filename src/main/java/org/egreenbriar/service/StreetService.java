package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StreetService {

    @Value("${streets.csv.file}")
    private String streetFile = null;

    private final Set<String> streets = new TreeSet<>();
    
    @PostConstruct
    public void read() throws FileNotFoundException, IOException {
        String[] components = null;
        
        CSVReader streetReader = new CSVReader(new FileReader(streetFile));
        
        // Ignore the first line. It holds headings.
        streetReader.readNext();
        
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
     * @param streetFile the streetFile to set
     */
    public void setStreetFile(String streetFile) {
        this.streetFile = streetFile;
    }
    
    public boolean contains(final String street) {
        return this.streets.contains(street);
    }

    public boolean isMissing(final String street) {
        return !contains(street);
    }
}
