package com.trove.config;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static com.trove.utility.AppConstant.*;

@Configuration
public class MySqlDataSourceConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(DATABASE_URL_ADDRESS);
        dataSource.setUsername(System.getenv(ENVIRONMENT_VARIABLE_USERNAME));

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(System.getenv(ENVIRONMENT_VARIABLE_PASSWORD_ENCRYPTOR));

        dataSource.setPassword(textEncryptor.decrypt(System.getenv(ENVIRONMENT_VARIABLE_PASSWORD_DECRYPTOR)));
        return dataSource;
    }
}
