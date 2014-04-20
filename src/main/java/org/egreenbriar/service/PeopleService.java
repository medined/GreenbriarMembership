package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    @Value("${people.csv.file}")
    String peopleFile = null;

    private final Map<String, Person> people = new HashMap<>();

    @PostConstruct
    public void read() throws IOException {
        String[] components = null;

        int lineCount = 0;

        //PersonID, District,Block,HouseNumber,StreetName,LastName,FirstName,PhoneNumber,EmailAddress,UnlistedPhone,NoDirectory,Comments
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(peopleFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeopleService.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        while ((components = reader.readNext()) != null) {
            if (lineCount != 0) {
                String personId = components[0];
                String districtName = components[1];
                String blockName = components[2];
                String houseNumber = components[3];
                String streetName = components[4];
                String lastName = components[5];
                String firstName = components[6];
                String phone = components[7];
                String email = components[8];
                String unlisted = components[9];
                String noDirectory = components[10];
                String comment = components[11];

                Person person = new Person();
                person.setPk(personId);
                person.setBlockName(blockName);
                person.setDistrictName(districtName);
                person.setHouseNumber(houseNumber);
                person.setStreetName(streetName);
                person.setFirst(firstName);
                person.setEmail(email);
                person.setPhone(phone);
                person.setLast(lastName);
                person.setComment(comment);
                person.setNoDirectory(Boolean.parseBoolean(noDirectory));
                person.setUnlisted(Boolean.parseBoolean(unlisted));

                people.put(person.getPk(), person);
            }
            lineCount++;
        }
        reader.close();
    }
    
    public void addPerson(final Person person) {
        people.put(person.getPk(), person);
    }

    public Person getPerson(String personUuid) {
        return people.get(personUuid);
    }
    
    public Set<Person> getPeopleInBlock(final String blockName) {
        Set<Person> peopleInBlock = new TreeSet<>();
        for (Entry<String, Person> entry : people.entrySet()) {
            Person person = entry.getValue();
            if (person.getBlockName().equals(blockName)) {
                peopleInBlock.add(person);
            }
        }
        return peopleInBlock;
    }

    public Set<Person> getPeopleInHouse(final String houseNumber, final String streetName) {
        Set<Person> peopleInHouse = new TreeSet<>();
        for (Entry<String, Person> entry : people.entrySet()) {
            Person person = entry.getValue();
            if (person.inHouse(houseNumber, streetName)) {
                peopleInHouse.add(person);
            }
        }
        return peopleInHouse;
    }
    
    public void write() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(peopleFile)) {
            writer.println("PersonID, District,Block,HouseNumber,StreetName,LastName,FirstName,PhoneNumber,EmailAddress,UnlistedPhone,NoDirectory,Comments");
            for (Entry<String, Person> entry : people.entrySet()) {
                Person person = entry.getValue();
                final String id = person.getPk();
                final String districtName = person.getDistrictName();
                final String blockName = person.getBlockName();
                final String houseNumber = person.getHouseNumber();
                final String streetName = person.getStreetName();
                final String lastName = person.getLast();
                final String firstName = person.getFirst();
                final String phone = person.getPhone();
                final String email = person.getEmail();
                final boolean unlisted = person.isUnlisted();
                final boolean noDirectory = person.isNoDirectory();
                final String comment = person.getComment();
                StringBuilder buffer = new StringBuilder();
                buffer.append(id).append(",");
                buffer.append(districtName).append(",");
                buffer.append(blockName).append(",");
                buffer.append(houseNumber).append(",");
                buffer.append(streetName).append(",");
                buffer.append(lastName).append(",");
                buffer.append(firstName).append(",");
                buffer.append(phone).append(",");
                buffer.append(email).append(",");
                buffer.append(unlisted).append(",");
                buffer.append(noDirectory).append(",");
                buffer.append(comment);
                writer.println(buffer.toString());
            }
        }
    }

    public void deletePerson(Person person) {
        people.remove(person.getPk());
    }

}
