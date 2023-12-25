package ir.javid.iran.service;

import ir.javid.iran.model.Driver;
import ir.javid.iran.model.Provider;
import ir.javid.iran.model.Role;
import ir.javid.iran.model.User;
import ir.javid.iran.repository.DriverRepository;
import ir.javid.iran.repository.RoleRepository;
import ir.javid.iran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * author: Mr.javidmolaei
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User RegisterUser(User user){
        return this.userRepository.save(user);
    }

    public void processOAuthPostLogin(String username) {
        User existUser = userRepository.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);

            userRepository.save(newUser);
        }

    }

    public User findById(Long id){
        Optional<User> byId = userRepository.findById(id);
        return byId.get();
    }

    public User findByPhone(String phoneNumber){
       return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void DeleteUser(User user){
        this.userRepository.delete(user);
    }

    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }


    public void saveUser(User u) {
        this.userRepository.save(u);
    }
}
