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
                    "        http://localhost:8080/#/user/verify/" + verificationHash + "\n" +
                    "        If you received this email but did not sign up to Project Ideas you can safely ignore this email.\n" +
                    "        Best regards,\n" +
                    "        E-Comm Team";
            String htmlText = "<p>Hi " + username + "!</p>\n" +
                    "    <p>We're happy you signed up for E-comm.</p>\n" +
                    "    <p>One last step. Simply click the link below to verify your email address.</p>\n" +
                    "    <a href=\"http://localhost:8080/#/user/verify/" + verificationHash + "\">\n" +
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

    public void sendPasswordResetEmail(String username, String email, String resetToken) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Password reset request");
            helper.setFrom("Ecomm <stephenbrady365@gmail.com>");
            helper.setTo(email);
            String plainText = "Hi " +  username + ",\n" +
                    "    You recently requested to reset your password for Project Ideas. Simply copy the link below and paste it into your address bar to reset your password.\n" +
                    "    http://localhost:8080/#/resetpassword/" + resetToken + "\n" +
                    "    If you did not request a password reset, you can ignore this email. This reset link is only valid for one hour.\n" +
                    "    Best regards,\n" +
                    "    E-comm Team";
            String htmlText = "<p>Hi " + username + ",</p>\n" +
                    "    <p> You recently requested to reset your password for Project Ideas.</p>\n" +
                    "    <a href=\"http://localhost:8080/#/resetpassword/" + resetToken + "\">Reset password</a>\n" +
                    "    <p>If you did not request a password reset, you can ignore this email. This reset link is only valid for one hour.</p>\n" +
                    "    <p>Best regards,</p>\n" +
                    "    <p>E-comm Team</p>";
            helper.setText(plainText, htmlText);
            emailSender.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    public void sendPasswordChangedEmail(String username, String email) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Your password was recently changed");
            helper.setFrom("Ecomm <stephenbrady365@gmail.com>");
            helper.setTo(email);
            String plainText = "Hi " +  username + ",\n" +
                    "    Your password has been successfully changed.\n" +
                    "    Didn't change your password? Please contact support at\n" +
                    "    stephenbrady365@gmail.com\n" +
                    "    to rectify the situation.\n" +
                    "    Best regards,\n" +
                    "    E-comm Team";
            String htmlText = "<p>Hi " + username + ",</p>\n" +
                    "    <p>Your password has been successfully changed.</p>\n" +
                    "    <p>Didn't change your password? Please contact support at \n" +
                    "    stephenbrady365@gmail.com \n" +
                    "    to rectify the situation.</p>" +
                    "    <p>Best regards,</p>\n" +
                    "    <p>E-comm Team</p>";
            helper.setText(plainText, htmlText);
            emailSender.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
