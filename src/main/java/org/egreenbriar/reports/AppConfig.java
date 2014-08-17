package org.egreenbriar.reports;

import org.egreenbriar.service.HouseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Bean
    public HouseService houseService() {
        return  new HouseService();
    }

}
