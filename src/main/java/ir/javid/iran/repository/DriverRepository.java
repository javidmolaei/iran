package ir.javid.iran.repository;

import ir.javid.iran.model.Driver;
import ir.javid.iran.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * author: Mr.javidmolaei
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Driver findByUser(User registerUser);

}
