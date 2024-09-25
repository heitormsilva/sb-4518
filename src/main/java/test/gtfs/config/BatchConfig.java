package test.gtfs.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.gtfs.batch.etl.EtlFlowConfig;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
	private static final String STEP_ETL = "flowStepEtl";
	private static final String RUN_CURRENT_DATE = "run.currentDate";

	private final JobRepository jobRepository;
	private final EtlFlowConfig etlFlow;

	private static final JobParametersIncrementer INCREMENTER = i -> new JobParametersBuilder()
			.addLocalDateTime(RUN_CURRENT_DATE, LocalDateTime.now())
			.toJobParameters();

	private static final DefaultJobParametersValidator VALIDATOR = new DefaultJobParametersValidator(
			new String[]{RUN_CURRENT_DATE},
			new String[]{""});

	@Bean
	Job job() {
		return new JobBuilder("job", jobRepository)
				.validator(VALIDATOR)
				.incrementer(INCREMENTER)
				.start(stepEtl())
				.build();
	}

	@Bean(STEP_ETL)
	Step stepEtl() {
		return new StepBuilder(STEP_ETL, jobRepository)
				.flow(etlFlow.etlFlow())
				.build();
	}
}