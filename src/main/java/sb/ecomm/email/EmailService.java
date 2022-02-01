package sb.ecomm.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendVerificationEmail(String username, String email, String verificationHash) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Please confirm your email address");
            helper.setFrom("Ecomm <stephenbrady365@gmail.com>");
            helper.setTo(email);
            String plainText = "Hi " + username + ",\n" +
                    "        We're happy you signed up for E-comm.\n" +
                    "        One last step. Simply copy the link below and paste it into your address bar to verify your email address.\n" +
                    "        http://localhost:8080/api/v1/auth/verify/" + verificationHash + "\n" +
                    "        If you received this email but did not sign up to Project Ideas you can safely ignore this email.\n" +
                    "        Best regards,\n" +
                    "        E-Comm Team";
            String htmlText = "<p>Hi " + username + "!</p>\n" +
                    "    <p>We're happy you signed up for E-comm.</p>\n" +
                    "    <p>One last step. Simply click the link below to verify your email address.</p>\n" +
                    "    <a href=\"http://localhost:8080/api/v1/auth/verify/" + verificationHash + "\">\n" +
                    "    Verify email address</a>\n" +
                    "    <p>If you did not request a password reset, you can ignore this email. This reset link is only valid for one hour.</p>\n" +
                    "    <p>Best regards,</p>\n" +
                    "    <p>E-Comm Team</p>";
            helper.setText(plainText, htmlText);
            emailSender.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }
}
