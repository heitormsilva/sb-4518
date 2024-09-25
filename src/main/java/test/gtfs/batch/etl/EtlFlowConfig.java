package test.gtfs.batch.etl;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EtlFlowConfig {
	public static final String ETL_FLOW = "etlFlow";
	private final AgencyFlowEtlConfig agencyFlowEtl;

	@Bean
	public Flow etlFlow() {
		return new FlowBuilder<Flow>(ETL_FLOW)
				.start(agencyFlowEtl.flow())
				.build();
	}
}
