package ir.javid.iran.email;

public interface EmailService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails emailDetails);
}
