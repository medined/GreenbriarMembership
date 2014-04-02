package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.Block;
import org.egreenbriar.model.Community;
import org.egreenbriar.model.District;
import org.egreenbriar.model.House;
import org.egreenbriar.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    @Value("${membership.csv.file}")
    String membershipFile = null;

    @Value("${houses.csv.file}")
    String housesFile = null;

    private final Community community = new Community();
    private final Map<String, Person> people = new HashMap<>();
    private final Map<String, House> houses = new HashMap<>();
    private final Map<String, String> breadcrumbs = new LinkedHashMap<>();
    private final Map<String, Block> blocks = new TreeMap<>();

    @Autowired
    private BlockCaptainService blockCaptainService = null;

    @Autowired
    private OfficierService officierService = null;

    int lineCount = 0;

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
                String y2012 = components[7];
                String y2013 = components[8];
                String email = components[9];
                String listedPhone = components[10];
                String noDirectory = components[11];
                String comment = components[12];

                District district = getCommunity().addDistrict(districtName);
                Block block = district.addBlock(district, blockName);
                House house = block.addHouse(houseNumber, streetName);
                Person person = house.addPerson(last, first, phone, email, comment);

                district.setRepresentative(officierService.get(district.getName()));
                getBlocks().put(blockName, block);
                block.addPerson(person);
                people.put(person.getPk(), person);
                getHouses().put(house.getUuid(), house);

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
                    house.setMember2012(true);
                }
                if (!y2013.isEmpty()) {
                    house.setMember2013(true);
                }

                String captainName = blockCaptainService.getCaptains().get(block.getBlockName());
                block.setCaptainName(captainName);

            }
            lineCount++;
        }

    }

    /**
     * @return the community
     */
    public Community getCommunity() {
        return community;
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
        return getHouses().get(houseUuid);
    }

    public void setOfficierService(OfficierService officierService) {
        this.officierService = officierService;
    }

    public Map<String, House> getHouses() {
        return houses;
    }

    public Map<String, String> getBreadcrumbs() {
        return breadcrumbs;
    }

    public Map<String, Block> getBlocks() {
        return blocks;
    }

    /**
     * @param blockCaptainService the blockCaptainService to set
     */
    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

    public Map<String, Block> getBlocksWithoutCaptains() {
        Map<String, Block> blocksWithoutCaptain = new TreeMap<>();
        for (Entry<String, Block> entry : blocks.entrySet()) {
            Block block = entry.getValue();
            String captainName = block.getCaptainName().trim();
            if (captainName.isEmpty()) {
                blocksWithoutCaptain.put(entry.getKey(), block);
            }
        }
        return blocksWithoutCaptain;
    }

    public void write() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(housesFile)) {
            writer.println("District,Block,HouseNumber,StreetName,2012,2013,2014");
            for (District district : community.getDistricts()) {
                final String districtName = district.getName();
                for (Block block : district.getBlocks()) {
                    final String blockName = block.getBlockName();
                    for (House house : block.getHouses()) {
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
                        /*
                        for (Person person : house.getPeople()) {
                            final String lastName = person.getLast();
                            final String firstName = person.getFirst();
                            final String phone = person.getPhone();
                            final String email = person.getEmail();
                            final boolean unlisted = person.isUnlisted();
                            final boolean noDirectory = person.isNoDirectory();
                            final String comment = person.getComment();
                        }
                        */
                    }
                }
            }
        }
    }

}
