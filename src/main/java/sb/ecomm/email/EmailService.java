package sb.ecomm.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sb.ecomm.product.Product;
import sb.ecomm.user.Address;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;

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

    public void sendOrderConfirmationEmail(String username,
                                           String email,
                                           long orderNumber,
                                           Date datePlaced,
                                           Address shippingAddress,
                                           List<Product> orderProducts,
                                           double totalOrderCost,
                                           String currency) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Thank you for your purchase. Here's the confirmation of your order.");
            helper.setFrom("Ecomm <stephenbrady365@gmail.com>");
            helper.setTo(email);

            String orderDate = getOrderDateString(datePlaced);
            String currencySymbol = getCurrencySymbol(currency);
            String formattedShippingAddress = formatShippingAddressString(shippingAddress);
            String productsPlain = getProductListForPlainTextEmail(orderProducts);
            String productsHtml = getProductListForHtmlTextEmail(orderProducts);

            String plainText = "Order #" + orderNumber + "\n" +
                    "    Hi " + username + ",\n\n" +
                    "    Thank you for placing your order!\n\n" +
                    "    Order Date: " + orderDate + "\n" +
                    "    Order Total: " + currencySymbol + totalOrderCost + "\n\n" +
                    "    Shipping Address:\n" +
                    "    " + formattedShippingAddress + "\n" +
                    "    Items in order: \n" +
                    "    " + productsPlain + "\n" +
                    "    Best regards,\n" +
                    "    E-comm Team";

            String htmlText = "<p>Order #" + orderNumber + "</p>\n" +
                    "    <p>Hi " + username + ",</p>\n\n" +
                    "    <p>Thank you for placing your order!</p>\n\n" +
                    "    <p>Order Date: " + orderDate + "</p>\n" +
                    "    <p>Order Total: " + currencySymbol + totalOrderCost + "</p>\n\n" +
                    "    <p>Shipping Address:</p>\n" +
                    "    <p>" + formattedShippingAddress + "</p>\n" +
                    "    Items in order: \n<ul>" +
                    "    " + productsPlain + "</ul>\n" +
                    "    <p>Best regards,</p>\n" +
                    "    <p>E-comm Team</p>";

            helper.setText(plainText, htmlText);
            emailSender.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private String formatShippingAddressString(Address address) {
        StringBuilder sb = new StringBuilder();
        sb.append(address.getName() + "\n");
        if (address.getLine1() != null) sb.append(address.getLine1() + "\n");
        if (address.getLine2() != null) sb.append(address.getLine2() + "\n");
        if (address.getLine3() != null) sb.append(address.getLine3() + "\n");
        if (address.getCity() != null) sb.append(address.getCity() + "\n");
        if (address.getProvince() != null) sb.append(address.getProvince() + "\n");
        if (address.getCountry() != null) sb.append(address.getCountry() + "\n");
        if (address.getZipCode() != null) sb.append(address.getZipCode() + "\n");
        return sb.toString();
    }

    private String getCurrencySymbol(String currency) {
        switch (currency) {
            case "usd":
                return "$";
            case "gbp":
                return "£";
            default:
                return "€";
        }
    }

    private String getOrderDateString(Date orderDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(orderDate);
    }

    private String getProductListForPlainTextEmail(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        products.forEach(product -> {
            sb.append("Name: " + product.getName() + ", size: " + product.getSize() + "\n");
        });

        return sb.toString();
    }

    private String getProductListForHtmlTextEmail(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        products.forEach(product -> {
            sb.append("<li>Name: " + product.getName() + ", size: " + product.getSize() + "</li>\n");
        });

        return sb.toString();
    }
}
