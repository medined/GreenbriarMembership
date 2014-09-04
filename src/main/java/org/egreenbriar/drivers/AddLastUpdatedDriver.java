package org.egreenbriar.drivers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import org.egreenbriar.model.Person;
import org.egreenbriar.service.ChangeService;
import org.egreenbriar.service.PeopleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AddLastUpdatedDriver {

    private PeopleService peopleService = null;
    
    private ChangeService changeService = null;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AddLastUpdatedDriver driver = new AddLastUpdatedDriver();
        driver.process(args, context);
    }

    private void process(final String[] args, final ApplicationContext context) throws FileNotFoundException, IOException {
        changeService = context.getBean("changeService", ChangeService.class);
        peopleService = context.getBean("peopleService", PeopleService.class);
        Set<String> people = peopleService.getPeople();
        for (String personUuid : people) {
            Person person = peopleService.getPerson(personUuid);
            person.setUpdatedBy("Unknown");
            person.setDateUpdated(changeService.findLastChangeForPerson(personUuid));
            System.out.println("U: " + personUuid + " -> " + person.getDateUpdated());
        }
        peopleService.write();
        System.out.println("Updated file: " + peopleService.getPeopleFile());
    }

    public PeopleService getPeopleService() {
        return peopleService;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    public ChangeService getChangeService() {
        return changeService;
    }

    public void setChangeService(ChangeService changeService) {
        this.changeService = changeService;
    }

}
