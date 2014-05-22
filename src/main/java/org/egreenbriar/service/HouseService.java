package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.House;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

    @Value("${houses.csv.file}")
    String housesFile = null;

    private final Map<String, House> houses = new TreeMap<>();

    @PostConstruct
    public void read() {
        String[] components;
        int lineCount = 0;

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(housesFile));
            while ((components = reader.readNext()) != null) {
                if (lineCount != 0) {
                    String id = components[0];
                    String districtName = components[1];
                    String blockName = components[2];
                    String houseNumber = components[3];
                    String streetName = components[4];
                    String y2012 = components[5];
                    String y2013 = components[6];
                    String y2014 = components[7];

                    House house = new House();
                    house.setId(id);
                    house.setDistrictName(districtName);
                    house.setBlockName(blockName);
                    house.setHouseNumber(houseNumber);
                    house.setStreetName(streetName);
                    house.setMember2012(Boolean.parseBoolean(y2012));
                    house.setMember2013(Boolean.parseBoolean(y2013));
                    house.setMember2014(Boolean.parseBoolean(y2014));
                    houses.put(id, house);
                }
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
    }

    public House getHouse(String houseUuid) {
        return getHouses().get(houseUuid);
    }

    public Map<String, House> getHouses() {
        return houses;
    }

    public int getNumberOfHousesInBlock(final String blockName) {
        return getHousesInBlock(blockName).size();
    }
    
    public Set<House> getHousesInBlock(final String blockName) {
        Set<House> rv = new TreeSet<>();
        for (Entry<String, House> entry : houses.entrySet()) {
            House house = entry.getValue();
            if (house.getBlockName().equals(blockName)) {
                rv.add(house);
            }
        }
        return rv;
    }

    public int getPercentMembership(final String blockName, String year) {
        Set<House> housesInBlock = getHousesInBlock(blockName);
        int numHouses = housesInBlock.size();
        int numMembers = 0;
        for (House house : housesInBlock) {
            if (house.memberInYear(year)) {
                numMembers++;
            }
        }
        return (int)(((float)numMembers / (float)numHouses) * 100);
    }

    public void write() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(housesFile)) {
            writer.println("HouseId,DistrictName,BlockName,HouseNumber,StreetName,2012,2013,2014");
            for (Entry<String, House> entry : houses.entrySet()) {
                House house = entry.getValue();
                final String id = house.getId();
                final String districtName = house.getDistrictName();
                final String blockName = house.getBlockName();
                final String houseNumber = house.getHouseNumber();
                final String streetName = house.getStreetName();
                final boolean member2012 = house.isMember2012();
                final boolean member2013 = house.isMember2013();
                final boolean member2014 = house.isMember2014();
                StringBuilder buffer = new StringBuilder();
                buffer.append(id).append(",");
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
