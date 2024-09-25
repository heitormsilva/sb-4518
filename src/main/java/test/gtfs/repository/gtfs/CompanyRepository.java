package test.gtfs.repository.gtfs;

import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test.gtfs.model.gtfs.entity.Company;

@Repository
@Transactional(readOnly = true)
public interface CompanyRepository extends ListPagingAndSortingRepository<Company, Long> {
}
