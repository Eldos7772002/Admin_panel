package kz.kazpost.dp.core.storage.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@ConfigurationProperties("spring.datasource-master")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMaster",
        transactionManagerRef = "transactionManagerMaster",
        basePackages = {"kz.kazpost.dp.core.storage.domain.repository"}
)
public class DataSourceConfMaster extends HikariConfig {
    public final static String PERSISTENCE_UNIT_NAME = "master";

    @Bean
    public HikariDataSource dataSourceMaster() {
        return new HikariDataSource(this);
    }

    @Bean("jpaProperties")
    @ConfigurationProperties("spring.datasource-master.jpa.properties")
    public Properties jpaProperties() {
        return new Properties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMaster(
            final HikariDataSource dataSourceMaster, final Properties jpaProperties) {

        return new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(dataSourceMaster);
            setPersistenceProviderClass(HibernatePersistenceProvider.class);
            setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
            setPackagesToScan("kz.kazpost.dp.core.storage.domain.model");
            setJpaProperties(jpaProperties);
        }};
    }

    @Bean
    public PlatformTransactionManager transactionManagerMaster(
            EntityManagerFactory entityManagerFactoryMaster) {
        return new JpaTransactionManager(entityManagerFactoryMaster);
    }
}
