package test.gtfs.repository.gtfs;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import test.gtfs.model.gtfs.entity.Agency;

@Repository
public interface AgencyRepository extends ListCrudRepository<Agency, Long>
		, ListPagingAndSortingRepository<Agency, Long> {
}
