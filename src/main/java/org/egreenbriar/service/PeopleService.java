package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.egreenbriar.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    @Value("${people.csv.file}")
    private String peopleFile = null;

    private final Map<String, Person> people = new HashMap<>();

    @PostConstruct
    public void initialize() {
        String[] components;

        int lineCount = 0;

        //PersonID, District,Block,HouseNumber,StreetName,LastName,FirstName,PhoneNumber,EmailAddress,UnlistedPhone,NoDirectory,Comments
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(getPeopleFile()));
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
                    String updatedBy = components[11];
                    String dateUpdated = components[12];
                    String comment = components[13];

                    Person person = new Person();
                    person.setPk(personId);
                    person.setDistrictName(districtName);
                    person.setBlockName(blockName);
                    person.setHouseNumber(houseNumber);
                    person.setStreetName(streetName);
                    person.setLast(lastName);
                    person.setFirst(firstName);
                    person.setPhone(phone);
                    person.setEmail(email);
                    person.setUnlisted(Boolean.parseBoolean(unlisted));
                    person.setNoDirectory(Boolean.parseBoolean(noDirectory));
                    person.setUpdatedBy(updatedBy);
                    person.setDateUpdated(dateUpdated);
                    person.setComment(comment);

                    people.put(person.getPk(), person);
                }
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to open " + getPeopleFile(), e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read " + getPeopleFile(), e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public void addPerson(final Person person) {
        people.put(person.getPk(), person);
    }

    // overlay previous information.
    public void updatePerson(final Person person) {
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

    public Set<Person> getPeopleInHouse(final String houseNumber, final String streetName, final Boolean emptySetFlag) {
        Set<Person> peopleInHouse = new TreeSet<>();
        for (Entry<String, Person> entry : people.entrySet()) {
            Person person = entry.getValue();
            if (person.inHouse(houseNumber, streetName)) {
                peopleInHouse.add(person);
            }
        }
        if (emptySetFlag && peopleInHouse.isEmpty()) {
            peopleInHouse.add(personEmptySet());
        }
        return peopleInHouse;
    }

    public void write() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(getPeopleFile())) {
            writer.println("PersonID, District,Block,HouseNumber,StreetName,LastName,FirstName,PhoneNumber,EmailAddress,UnlistedPhone,NoDirectory,UpdatedBy,DateUpdated,Comments");
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
                final String updatedBy = person.getUpdatedBy();
                final String dateUpdated = person.getDateUpdated();
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
                buffer.append(updatedBy).append(",");
                buffer.append(dateUpdated).append(",");
                buffer.append(comment);
                writer.println(buffer.toString());
            }
        }
    }

    public void deletePerson(Person person) {
        getPeople().remove(person.getPk());
    }

    public Set<String> getPeople() {
        return people.keySet();
    }

    public Set<Person> getPeopleWithBadEmails() {
        Set<Person> peopleWithBadEmails = new TreeSet<>();
        for (String personId : people.keySet()) {
            Person person = getPerson(personId);
            if (person.hasBadEmail()) {
                peopleWithBadEmails.add(person);
            }
        }
        return peopleWithBadEmails;
    }

    public String getPeopleFile() {
        return peopleFile;
    }

    public void setPeopleFile(String peopleFile) {
        this.peopleFile = peopleFile;
    }

    public List<Person> getPeopleWithEmail(String email) {
        List<Person> peopleWithEmail = new ArrayList<>();
        for (Entry<String, Person> entry : people.entrySet()) {
            Person person = entry.getValue();
            if (person.getEmail().equals(email)) {
                peopleWithEmail.add(person);
            }
        }
        return peopleWithEmail;
    }
    
    private Person personEmptySet() {
        
                    String personId = " ";
                    String districtName = " ";
                    String blockName = " ";
                    String houseNumber = " ";
                    String streetName = " ";
                    String lastName = " ";
                    String firstName = " ";
                    String phone = " ";
                    String email = " ";
                    String unlisted = " ";
                    String noDirectory = " ";
                    String updatedBy = " ";
                    String dateUpdated = " ";
                    String comment = " ";

                    Person person = new Person();
                    person.setPk(personId);
                    person.setDistrictName(districtName);
                    person.setBlockName(blockName);
                    person.setHouseNumber(houseNumber);
                    person.setStreetName(streetName);
                    person.setLast(lastName);
                    person.setFirst(firstName);
                    person.setPhone(phone);
                    person.setEmail(email);
                    person.setUnlisted(Boolean.parseBoolean(unlisted));
                    person.setNoDirectory(Boolean.parseBoolean(noDirectory));
                    person.setUpdatedBy(updatedBy);
                    person.setDateUpdated(dateUpdated);
                    person.setComment(comment);

                    //people.put(person.getPk(), person);
                    
                    return(person);
                }
}
