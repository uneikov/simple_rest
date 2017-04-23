package com.uran;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.slf4j.LoggerFactory.getLogger;


@SpringBootApplication
@EnableTransactionManagement
@Import({RestSecurityConfig.class, DataSourceConfig.class, ScheduledTaskConfig.class})
@PropertySource(value= {"classpath:application.properties"})
@EnableJpaRepositories(basePackages = "com.uran.repository")
public class SimpleRestApplication extends SpringBootServletInitializer {
    private static final Logger LOG = getLogger(SimpleRestApplication.class);
    
    public static int NUMBER_OF_HORSES_FOR_RACE;
    @Value("${station.number.of.horses.for.race:6}")
    public void setNumberOfHorsesForRace(int value) {
        NUMBER_OF_HORSES_FOR_RACE = value;
    }
    
	public static void main(String[] args) {
		SpringApplication.run(SimpleRestApplication.class, args);
	}
    
    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SimpleRestApplication.class);
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

/*@Bean(name = "authProvider")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }*/

/* @Configuration
 @EnableScheduling
 public class AppConfig implements SchedulingConfigurer {
     @Override
     public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
         taskRegistrar.setScheduler(taskScheduler());
         taskRegistrar.addTriggerTask(
             new Runnable() {
                 myTask().work();
             },
             new CustomTrigger()
         );
     }

     @Bean
     public Executor taskScheduler() {
         return Executors.newScheduledThreadPool(42);
     }

     @Bean
     public MyTask myTask() {
         return new MyTask();
     }
 }*/