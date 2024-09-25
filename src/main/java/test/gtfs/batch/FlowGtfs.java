package test.gtfs.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.flow.Flow;

public interface FlowGtfs {
	Flow flow();

	Step step();
}
