package ua.com.khpi.database.yegorchevardin.lab07.program.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Class for configuring spring and program start up
 * @author yegorchevardin
 * @version 0.0.1
 */
@Configuration
public class ProgramConfiguration {
    /**
     * Configuring Hibernate validation bean
     * @return MethodValidationProcessor object
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
