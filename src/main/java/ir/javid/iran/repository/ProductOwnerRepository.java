package ir.javid.iran.repository;

import ir.javid.iran.model.Marketer;
import ir.javid.iran.model.ProductOwner;
import ir.javid.iran.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author: Mr.javidmolaei
 */
@Repository
public interface ProductOwnerRepository extends JpaRepository<ProductOwner, Long> {

    ProductOwner findByUser(User registerUser);

}
