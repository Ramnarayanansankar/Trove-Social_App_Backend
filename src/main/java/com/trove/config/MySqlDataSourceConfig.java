package com.trove.config;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class MySqlDataSourceConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/trove");
        dataSource.setUsername(System.getenv("user"));

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(System.getenv("key"));

        dataSource.setPassword(textEncryptor.decrypt(System.getenv("password")));
        return dataSource;
    }
}
