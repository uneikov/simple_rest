package com.uran;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;


@Configuration
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAutoConfiguration()
public class DataSourceConfig {

    @Bean(name = "h2", destroyMethod = "")
    @ConfigurationProperties(prefix = "application-h2")
    @Profile("h2")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                addScript("schema-h2.sql").
                addScript("data-h2.sql").
                build();
    }


    @Profile("postgres")
    //@Bean(name = "postgres")
    //@ConfigurationProperties(prefix = "application-postgres")
    public @Bean DataSource postgresDataSource() {
        //return  DataSourceBuilder.create().build();
        return DataSourceBuilder
                .create()
                .username("gsuser")
                .password("password")
                .url("jdbc:postgresql://localhost:5432/gsdb")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /*@Profile("postgres")
    public @Bean DataSource _dataSource() {
        DriverManagerDataSource ret = new DriverManagerDataSource();
        ret.setDriverClassName("org.postgresql.Driver");
        ret.setUsername("gsuser");
        ret.setPassword("password");
        ret.setUrl("jdbc:postgresql://localhost:5432/gsdb");
        return ret;
    }*/

}
