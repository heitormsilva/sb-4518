package test.gtfs.batch.etl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import test.gtfs.model.gtfs.entity.Company;
import test.gtfs.repository.gtfs.CompanyRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@SpringBatchTest
class AgencyFlowEtlConfigTest {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Mock
	private CompanyRepository companyRepository;

	@Autowired
	private Job job;

	@Test
	void step() throws Exception {
		//GIVEN
		Company company = new Company(1L, "name company", "www");
		Page<Company> page = new PageImpl<>(List.of(company));

		//WHEN
		when(companyRepository.findAll(any(Pageable.class))).thenReturn(page);
		var jobParameters = new JobParametersBuilder()
				.addLocalDateTime("run.currentDate", LocalDateTime.now())
				.toJobParameters();

//		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//		jobLauncherTestUtils.setJob(job);
//		JobExecution jobExecution = jobLauncherTestUtils.launchStep("flowStepEtl");
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("agencyStepEtl");

		//THEN
		StepExecution stepExecution = jobExecution.getStepExecutions().stream().findFirst().get();
		assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
//		assertEquals(1, stepExecution.getReadCount());
		assertEquals(1, stepExecution.getWriteCount());
	}
}