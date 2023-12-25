package ir.javid.iran.service;

import ir.javid.iran.model.Marketer;
import ir.javid.iran.repository.MarketerRepository;
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
public class MarketerService {

    private final MarketerRepository marketerRepository;
    private final UserRepository userRepository;


    @Autowired
    public MarketerService(MarketerRepository marketerRepository, UserRepository userRepository) {
        this.marketerRepository = marketerRepository;
        this.userRepository = userRepository;
    }


    public Boolean saveMarketer(Marketer marketer) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (marketerRepository.findByUser(userRepository.getUserByUsername(auth.getName()) ) == null) {
//            marketer.setMarketerPictureText(this.saveFile(marketer.getMarketerPicture()));
            marketer.setMarketerPictureFile(marketer.getMarketerPicture().getBytes());
//            marketer.setBirthCertificatePictureText(this.saveFile(marketer.getBirthCertificatePicture()));
            marketer.setBirthCertificatePictureFile(marketer.getBirthCertificatePicture().getBytes());
//            marketer.setAffidavitPictureText(this.saveFile(marketer.getAffidavitPicture()));
            marketer.setAffidavitPictureFile(marketer.getAffidavitPicture().getBytes());
//            marketer.setNationalCardPictureText(this.saveFile(marketer.getNationalCardPicture()));
            marketer.setNationalCardPictureFile(marketer.getNationalCardPicture().getBytes());
            marketerRepository.save(marketer);
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
    public List<Marketer> getDrivers(){
        return marketerRepository.findAll();
    }
}
