package test.gtfs.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import test.gtfs.model.gtfs.entity.Agency;
import test.gtfs.repository.gtfs.AgencyRepository;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "gtfsEntityManagerFactory",
		transactionManagerRef = "gtfsTransactionManager",
		basePackageClasses = AgencyRepository.class)
@EnableTransactionManagement
public class GtfsEntityManagerFactoryConfig {
	private static final String EM_NAME = "gtfs";
	private static final String EM_FACTORY = EM_NAME + "EntityManagerFactory";

	@Bean(EM_FACTORY)
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder, @Qualifier(EM_NAME + "DataSource") DataSource dataSource) {

		return builder.dataSource(dataSource)
				.packages(Agency.class)
				.persistenceUnit(EM_NAME + "-pu")
				.build();
	}

	@Bean(EM_NAME + "TransactionManager")
	@Primary
	public PlatformTransactionManager transactionManager(
			@Qualifier(EM_FACTORY) EntityManagerFactory entityManagerFactory) {

		return new JpaTransactionManager(entityManagerFactory);
	}
}
