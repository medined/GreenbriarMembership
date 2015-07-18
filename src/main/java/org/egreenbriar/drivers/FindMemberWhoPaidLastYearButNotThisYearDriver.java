package org.egreenbriar.drivers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.egreenbriar.model.House;
import org.egreenbriar.model.Person;
import org.egreenbriar.service.HouseService;
import org.egreenbriar.service.PeopleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FindMemberWhoPaidLastYearButNotThisYearDriver {

    private HouseService houseService = null;
    
    private PeopleService peopleService = null;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        FindMemberWhoPaidLastYearButNotThisYearDriver driver = new FindMemberWhoPaidLastYearButNotThisYearDriver();
        driver.process(args, context);
    }

    private void process(final String[] args, final ApplicationContext context) throws FileNotFoundException, IOException {
        houseService = context.getBean("houseService", HouseService.class);
        peopleService = context.getBean("peopleService", PeopleService.class);
        int count = 0;
        Map<String, House> houses = houseService.getHouses();
        for (Entry<String, House> entry : houses.entrySet()) {
            boolean isMember2014 = entry.getValue().isMember2014();
            boolean isMember2015 = entry.getValue().isMember2015();
            String houseNumber = entry.getValue().getHouseNumber();
            String streetName = entry.getValue().getStreetName();
            if (isMember2014 && !isMember2015) {
                Set<Person> people = peopleService.getPeopleInHouse(houseNumber, streetName, false);
                String email = null;
                for (Person person : people) {
                    if (person.getEmail().isEmpty() == false) {
                        email = person.getEmail();
                        System.out.print(email + ",");
                        count++;
                    }
                }
            }
        }
        System.out.println("Count: " + count);
    }

    public HouseService getHouseService() {
        return houseService;
    }

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public PeopleService getPeopleService() {
        return peopleService;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

}
