package ir.javid.iran.repository;

import ir.javid.iran.model.TempCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempCodeRepository extends JpaRepository<TempCode, Long> {

}
