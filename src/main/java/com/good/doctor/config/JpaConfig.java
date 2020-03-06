package com.good.doctor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Value("${good.doctor.database.driver}")
    private String driver;
    @Value("${good.doctor.database.host}")
    private String host;
    @Value("${good.doctor.database.port}")
    private String port;
    @Value("${good.doctor.database.user}")
    private String user;
    @Value("${good.doctor.database.password}")
    private String password;

    @Bean
    public DataSource getDataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url("jdbc:mysql://"+host+":"+port+"/appl_gd");
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
