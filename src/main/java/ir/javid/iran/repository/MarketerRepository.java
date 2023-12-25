package ir.javid.iran.repository;

import ir.javid.iran.model.Driver;
import ir.javid.iran.model.Marketer;
import ir.javid.iran.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author: Mr.javidmolaei
 */
@Repository
public interface MarketerRepository extends JpaRepository<Marketer, Long> {
    Marketer findByUser(User registerUser);


}
