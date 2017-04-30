package com.uran;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;


@Configuration
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAutoConfiguration()
//@PropertySource("application-postgres.properties")
public class DataSourceConfig {

    /*@Bean(name = "h2", destroyMethod = "")
    @ConfigurationProperties(prefix = "application-h2")
    @Profile("h2")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                addScript("schema-h2.sql").
                addScript("data-h2.sql").
                build();
    }*/

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
