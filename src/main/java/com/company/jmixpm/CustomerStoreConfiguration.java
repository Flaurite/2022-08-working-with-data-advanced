package com.company.jmixpm;

import io.jmix.autoconfigure.data.JmixLiquibaseCreator;
import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import io.jmix.data.persistence.DbmsSpecifics;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class CustomerStoreConfiguration {

    @Bean
    @ConfigurationProperties("customer.datasource")
    DataSourceProperties customerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "customer.datasource.hikari")
    DataSource customerDataSource(@Qualifier("customerDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
            @Qualifier("customerDataSource") DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter,
            DbmsSpecifics dbmsSpecifics,
            JmixModules jmixModules,
            Resources resources) {
        return new JmixEntityManagerFactoryBean("customer", dataSource, jpaVendorAdapter, dbmsSpecifics, jmixModules, resources);
    }

    @Bean
    JpaTransactionManager customerTransactionManager(@Qualifier("customerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("customer", entityManagerFactory);
    }

    @Bean
    public SpringLiquibase customerLiquibase(LiquibaseChangeLogProcessor processor, @Qualifier("customerDataSource") DataSource dataSource) {
        return JmixLiquibaseCreator.create(dataSource, new LiquibaseProperties(), processor, "customer");
    }
}
