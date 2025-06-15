package com.gabrielnilsonespindola.salesSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.caelum.stella.validation.CPFValidator;

@Configuration
public class AppConfig {
	
	@Bean
    public CPFValidator cpfValidator() {
        return new CPFValidator();
    }

}
