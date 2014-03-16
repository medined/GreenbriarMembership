package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.Block;
import org.egreenbriar.model.District;
import org.egreenbriar.model.Greenbriar;
import org.egreenbriar.model.House;
import static org.egreenbriar.model.Membership.YEAR_2012;
import static org.egreenbriar.model.Membership.YEAR_2013;
import org.egreenbriar.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    @Value("${membership.csv.file}")
    String membershipFile = null;

    private final Greenbriar community = new Greenbriar();
    private final Map<String, Person> people = new HashMap<>();
    private final Map<String, House> houses = new HashMap<>();

    @Autowired
    private StreetService streetService = null;

    int lineCount = 0;
    int incorrectStreets = 0;

    @PostConstruct
    public void read() throws FileNotFoundException, IOException {
        String[] components = null;

        CSVReader reader = new CSVReader(new FileReader(membershipFile));
        while ((components = reader.readNext()) != null) {
            if (lineCount != 0) {
                String districtName = components[0];
                String blockName = components[1];
                String houseNumber = components[2];
                String streetName = components[3];
                String last = components[4];
                String first = components[5];
                String phone = components[6];
                String bc = components[7];
                String y2012 = components[8];
                String y2013 = components[9];
                String email = components[10];
                String listedPhone = components[11];
                String noDirectory = components[12];
                String comment = components[13];

                District district = getCommunity().addDistrict(districtName);
                Block block = district.addBlock(districtName, blockName);
                House house = block.addHouse(houseNumber, streetName);
                Person person = house.addPerson(last, first, phone, email, comment);
                
                block.addPerson(person);
                people.put(person.getPk(), person);
                houses.put(house.getUuid(), house);

                if (streetService.isMissing(streetName)) {
                    System.out.println("Incorrect Street: " + streetName);
                    incorrectStreets++;
                }

                if (listedPhone.equals("Unlisted")) {
                    person.setUnlisted(true);
                } else if (listedPhone.isEmpty()) {
                    // empty field is ok.
                } else {
                    System.out.println("Unknown ListedPhone value: " + last + "," + first + " - " + listedPhone);
                }
                
                if (noDirectory.equals("No List")) {
                    person.setNoDirectory(true);
                } else if (noDirectory.isEmpty()) {
                    // empty field is ok.
                } else {
                    System.out.println("Unknown noDiectory value: " + last + "," + first + " - " + noDirectory);
                }
                
                if (!y2012.isEmpty()) {
                    house.addYear(YEAR_2012);
                }
                if (!y2013.isEmpty()) {
                    house.addYear(YEAR_2013);
                }

                if (!bc.isEmpty()) {
                    block.addCaptain(person);
                }

            }
            lineCount++;
        }
    }

    /**
     * @return the community
     */
    public Greenbriar getCommunity() {
        return community;
    }

    /**
     * @param streetService the streetService to set
     */
    public void setStreetService(StreetService streetService) {
        this.streetService = streetService;
    }

    /**
     * @return the districts
     */
    public Set<District> getDistricts() {
        return community.getDistricts();
    }

    public District getDistrict(String name) {
        return community.getDistrict(name);
    }

    public Block getBlock(String name) {
        return community.getBlock(name);
    }

    public Person getPerson(String personUuid) {
        return people.get(personUuid);
    }

    public House getHouse(String houseUuid) {
        return houses.get(houseUuid);
    }

}
