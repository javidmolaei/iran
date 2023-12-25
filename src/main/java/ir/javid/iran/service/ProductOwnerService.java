package ir.javid.iran.service;

import ir.javid.iran.model.ProductOwner;
import ir.javid.iran.repository.ProductOwnerRepository;
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
import java.util.Objects;
import java.util.UUID;

/**
 * author: Mr.javidmolaei
 */

@Service
public class ProductOwnerService {

    private final ProductOwnerRepository productOwnerRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductOwnerService(ProductOwnerRepository productOwnerRepository, UserRepository userRepository) {
        this.productOwnerRepository = productOwnerRepository;
        this.userRepository = userRepository;
    }

    public Boolean saveProductOwner(ProductOwner productOwner) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (productOwnerRepository.findByUser(userRepository.getUserByUsername(auth.getName()) ) == null) {
//            productOwner.setActivityLicensePictureText(this.saveFile(productOwner.getActivityLicensePicture()));
            productOwner.setActivityLicensePictureFile(productOwner.getActivityLicensePicture().getBytes());
//            productOwner.setCompanyLogoText(this.saveFile(productOwner.getCompanyLogo()));
            productOwner.setCompanyLogoFile(productOwner.getCompanyLogo().getBytes());
            this.productOwnerRepository.save(productOwner);
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

}
