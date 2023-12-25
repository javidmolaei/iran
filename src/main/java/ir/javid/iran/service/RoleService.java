package ir.javid.iran.service;

import ir.javid.iran.model.Role;
import ir.javid.iran.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * author: Mr.javidmolaei
 */

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> roles() {
        return roleRepository.findAll();
    }

    public void initializeStates() {
        if (roleRepository.count() == 0) {
            addRole("SUPER_ADMIN");
            addRole("MANAGER");
            addRole("ADMIN");
            addRole("USER");
            addRole("DRIVER");
            addRole("MARKETER");
            addRole("PRODUCT_OWNER");
            addRole("TRANSPORTATION_INTERNAL");
            addRole("TRANSPORTATION_EXTERNAL");
            addRole("TRANSPORTATION_SHIPPING");
        }
    }


    private void addRole(String roleName) {
        roleRepository.save(new Role(roleName));
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }
}
