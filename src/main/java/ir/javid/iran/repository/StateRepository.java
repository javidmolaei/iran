package ir.javid.iran.repository;

import ir.javid.iran.model.Driver;
import ir.javid.iran.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
