package ir.javid.iran.repository;

import ir.javid.iran.model.Marketer;
import ir.javid.iran.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author: Mr.javidmolaei
 */
@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {


}
