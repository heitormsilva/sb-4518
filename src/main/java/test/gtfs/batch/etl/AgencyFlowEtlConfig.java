package test.gtfs.batch.etl;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaTransactionManager;
import test.gtfs.batch.FlowGtfs;
import test.gtfs.batch.ProcessGtfs;
import test.gtfs.batch.etl.processor.AgencyProcessorEtl;
import test.gtfs.config.AppProperties;
import test.gtfs.model.gtfs.entity.Agency;
import test.gtfs.model.gtfs.entity.Company;
import test.gtfs.repository.gtfs.AgencyRepository;
import test.gtfs.repository.gtfs.CompanyRepository;

import java.util.Arrays;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AgencyFlowEtlConfig implements FlowGtfs, ProcessGtfs<Company, Agency> {
	private static final String COMPONENT = "agency";
	private static final String FLOW = COMPONENT + "FlowEtl";
	private static final String STEP = COMPONENT + "StepEtl";
	private static final String ITEM_READER = COMPONENT + "ItemReaderEtl";
	private static final String VALIDATION_PROCESSOR = COMPONENT + "ValidationProcessorEtl";
	private static final String COMPOSITE_PROCESSOR = COMPONENT + "CompositeProcessorEtl";
	private static final String WRITER = COMPONENT + "WriterEtl";

	private final JobRepository jobRepository;
	private final JpaTransactionManager transactionManager;
	private final AgencyRepository agencyRepository;
	private final CompanyRepository companyRepository;
	private final AppProperties appProp;
	private final AgencyProcessorEtl agencyProcessorEtl;

	@Override
	@Bean(FLOW)
	public Flow flow() {
		return new FlowBuilder<Flow>(FLOW)
				.start(step())
				.build();
	}

	@Override
	@Bean(STEP)
	public Step step() {
		return new StepBuilder(STEP, jobRepository)
				.<Company, Agency>chunk(appProp.batch().pageSize(), transactionManager)
				.reader(reader())
				.processor(compositeProcessor())
				.writer(writer())
				.build();
	}

	@Override
	@Bean(ITEM_READER)
	public RepositoryItemReader<Company> reader() {
		return new RepositoryItemReaderBuilder<Company>()
				.name(ITEM_READER)
				.repository(companyRepository)
				.methodName("findAll")
				.pageSize(appProp.batch().pageSize())
				.sorts(Map.of("idCompany", Sort.Direction.ASC))
				.build();
	}

	@Override
	@Bean(VALIDATION_PROCESSOR)
	public BeanValidatingItemProcessor<Company> validationProcessor() {
		return new BeanValidatingItemProcessor<>();
	}

	@Override
	@Bean(COMPOSITE_PROCESSOR)
	public CompositeItemProcessor<Company, Agency> compositeProcessor() {
		CompositeItemProcessor<Company, Agency> itemProcessor = new CompositeItemProcessor<>();
		itemProcessor.setDelegates(Arrays.asList(
				validationProcessor(),
				agencyProcessorEtl));
		return itemProcessor;
	}

	@Override
	@Bean(WRITER)
	public RepositoryItemWriter<Agency> writer() {
		return new RepositoryItemWriterBuilder<Agency>()
				.repository(agencyRepository)
				.methodName("save")
				.build();
	}
}