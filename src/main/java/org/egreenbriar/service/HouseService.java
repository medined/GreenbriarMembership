package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.House;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

    @Value("${houses.csv.file}")
    String housesFile = null;

    private final Map<String, House> houses = new HashMap<>();

    @PostConstruct
    public void read() throws FileNotFoundException, IOException {
        String[] components = null;
        int lineCount = 0;

        CSVReader reader = new CSVReader(new FileReader(housesFile));
        while ((components = reader.readNext()) != null) {
            if (lineCount != 0) {
                String districtName = components[0];
                String blockName = components[1];
                String houseNumber = components[2];
                String streetName = components[3];
                String y2012 = components[4];
                String y2013 = components[5];
                String y2014 = components[6];

                House house = new House();
                house.setDistrictName(districtName);
                house.setBlockName(blockName);
                house.setHouseNumber(houseNumber);
                house.setStreetName(streetName);
                house.setMember2012(Boolean.parseBoolean(y2012));
                house.setMember2013(Boolean.parseBoolean(y2013));
                house.setMember2014(Boolean.parseBoolean(y2014));
                houses.put(house.getUuid(), house);
            }
            lineCount++;
        }
        
        System.out.println("houses: " + houses);

    }

    public House getHouse(String houseUuid) {
        return getHouses().get(houseUuid);
    }

    public Map<String, House> getHouses() {
        return houses;
    }

    public void write() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(housesFile)) {
            writer.println("District,Block,HouseNumber,StreetName,2012,2013,2014");
            for (Entry<String, House> entry : houses.entrySet()) {
                House house = entry.getValue();
                final String districtName = house.getDistrictName();
                final String blockName = house.getBlockName();
                final String houseNumber = house.getHouseNumber();
                final String streetName = house.getStreetName();
                final boolean member2012 = house.isMember2012();
                final boolean member2013 = house.isMember2013();
                final boolean member2014 = house.isMember2014();
                StringBuilder buffer = new StringBuilder();
                buffer.append(districtName).append(",");
                buffer.append(blockName).append(",");
                buffer.append(houseNumber).append(",");
                buffer.append(streetName).append(",");
                buffer.append(member2012).append(",");
                buffer.append(member2013).append(",");
                buffer.append(member2014);
                writer.println(buffer.toString());
            }
        }
    }

}
