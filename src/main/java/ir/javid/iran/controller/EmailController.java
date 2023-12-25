package ir.javid.iran.controller;

import ir.javid.iran.email.EmailDetails;
import ir.javid.iran.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: Mr.javidmolaei
 */

@RestController
@RequestMapping(value = "/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details){
        return emailService.sendSimpleMail(details);
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details){
        return emailService.sendMailWithAttachment(details);
    }

}
