package test.gtfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import test.gtfs.config.AppProperties;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = AppProperties.class)
public class GtfsApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(GtfsApplication.class, args)));
	}
}
