package test.gtfs.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
	@Primary
	@Bean
	@ConfigurationProperties("app.datasource.gtfs")
	public DataSourceProperties gtfsDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean
	@ConfigurationProperties("app.datasource.gtfs")
	public HikariDataSource gtfsDataSource(@Qualifier("gtfsDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder()
				.type(HikariDataSource.class)
				.build();
	}
}
