package test.gtfs.batch.etl.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import test.gtfs.config.AppProperties;
import test.gtfs.model.gtfs.entity.Agency;
import test.gtfs.model.gtfs.entity.Company;

@Component
public class AgencyProcessorEtl implements ItemProcessor<Company, Agency> {
	private final String urlBase;
	private static final String URL_PARAM = "";

	public AgencyProcessorEtl(AppProperties appProp) {
		this.urlBase = "";
	}

	@Override
	public Agency process(final Company company) {
		String url = urlBase + company.getUrl() + URL_PARAM;
		return new Agency(company.getName(),
				url,
				"America/Sao_Paulo");
	}
}
