package org.egreenbriar.reports;

import java.util.Set;
import java.util.TreeSet;
import org.egreenbriar.service.CountMembersService;
import org.egreenbriar.service.HouseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CountMembers {

    private CountMembersService countMembersService = null;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CountMembers driver = new CountMembers();
        driver.process(args, context);
    }

    private void process(final String[] args, final ApplicationContext context) {
        Set<String> lines = new TreeSet<String>();
        setCountMembersService(context.getBean("countMembersService", CountMembersService.class));
        String[] directories = getCountMembersService().getStorageDirectories();
        for (String directory : directories) {
            String houseFile = getCountMembersService().getStorageDirectory() + "/" + directory + "/houses.csv";
            HouseService houseService = new HouseService(houseFile);
            houseService.initialize();
            int percent2012 = houseService.getPercentMembership("2012");
            int percent2013 = houseService.getPercentMembership("2013");
            int percent2014 = houseService.getPercentMembership("2014");
            lines.add(String.format(" %s |  %02d  |  %02d  |  %02d  |", directory, percent2012, percent2013, percent2014));
        }
        System.out.println("----------------------------------");
        System.out.println(" Year       | 2012 | 2013 | 2014 |");
        System.out.println("----------------------------------");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("----------------------------------");
    }

    public CountMembersService getCountMembersService() {
        return countMembersService;
    }

    public void setCountMembersService(CountMembersService countMembersService) {
        this.countMembersService = countMembersService;
    }

}
