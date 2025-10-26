package br.edu.iftm.Customer.java.config;

import org.springframework.context.annotation.Bean;
import org.mockito.Mockito;
import br.edu.iftm.Customer.java.service.CustomerService;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestConfig {

    @Bean
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
  
    }
}
