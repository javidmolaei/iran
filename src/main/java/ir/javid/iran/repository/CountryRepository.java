package ir.javid.iran.repository;

import ir.javid.iran.model.Country;
import ir.javid.iran.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
