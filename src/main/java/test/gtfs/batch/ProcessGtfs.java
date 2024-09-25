package test.gtfs.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;

public interface ProcessGtfs<I, O> {
	ItemReader<I> reader();

	ValidatingItemProcessor<I> validationProcessor();

	CompositeItemProcessor<I, O> compositeProcessor();

	ItemWriter<O> writer();
}
