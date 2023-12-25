package ir.javid.iran.service;

import ir.javid.iran.model.Driver;
import ir.javid.iran.repository.DriverRepository;
import ir.javid.iran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * author: Mr.javidmolaei
 */

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, UserRepository userRepository) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    public Boolean saveDriver(Driver driver) throws IOException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (driverRepository.findByUser(userRepository.getUserByUsername(driver.getUser().getUsername()) ) == null) {
//            driver.setDriverPictureString(this.saveFile(driver.getDriverPicture()));
            driver.setDriverPictureFile(driver.getDriverPicture().getBytes());
//            driver.setCarCartPictureString(this.saveFile(driver.getCarCartPicture()));
            driver.setCarCartPictureFile(driver.getCarCartPicture().getBytes());
//            driver.setLicenseCardPictureString(this.saveFile(driver.getLicenseCardPicture()));
            driver.setLicenseCardPictureFile(driver.getLicenseCardPicture().getBytes());
//            driver.setNationalCardPictureString(this.saveFile(driver.getNationalCardPicture()));
            driver.setNationalCardPictureFile(driver.getNationalCardPicture().getBytes());
            driverRepository.save(driver);
            return true;
        }
        else System.out.println("شما قبلا ثبت نام کرده اید! ");
        return false;
    }

    public String saveFile(MultipartFile input) throws IOException {
        if (!input.isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/uploaddd").getAbsolutePath();
            byte[] bytes = input.getBytes();

            String name = UUID.randomUUID() + "." + Objects.requireNonNull(input.getContentType()).split("/")[1];
            Files.write(Paths.get(path + File.separator + name), bytes);
            return name;
        }
        return "NO Picture!";
    }

    public List<Driver> getDrivers() {
        return driverRepository.findAll();
    }
}
