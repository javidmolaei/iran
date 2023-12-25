package ir.javid.iran.service;

import ir.javid.iran.model.TempCode;
import ir.javid.iran.model.User;
import ir.javid.iran.repository.TempCodeRepository;
import ir.javid.iran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

/**
 * author: Mr.javidmolaei
 */

@Service
public class TempService {
    private final TempCodeRepository tempCodeRepository;

    private final UserRepository userRepository;

    @Autowired
    public TempService(TempCodeRepository tempCodeRepository, UserRepository userRepository) {
        this.tempCodeRepository = tempCodeRepository;
        this.userRepository = userRepository;
    }

    public String saveTempCode(User user) {
        TempCode tempCode = tempCodeRepository.save(new TempCode(generateRandomCode(4), new Date(System.currentTimeMillis()), user));
        return tempCode.getVerifyPhoneCode();
    }

    public String saveEmailCode(User user) {
        Optional<User> byId = userRepository.findById(user.getId());
        if (byId.isPresent()) {
            TempCode code = new TempCode(new Date(System.currentTimeMillis()), byId.get());
            code.setVerifyEmailCode(generateRandomCode(4));
            TempCode tempCode = tempCodeRepository.save(code);
            return tempCode.getVerifyPhoneCode();
        }
        return "ابتدا کاربری خود را ثبت کنید";
    }

    public String generateRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        int rNum = random.nextInt((int) Math.pow(10, length) - 1);
        return String.format("%0" + length + "d", rNum);
    }

}
