package com.example.foodzz_v2.foodzz_v2.util.mail;

public class MailTextUtil {

    public static String getVerificationMailBody(String username, String verificationLink){
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(username).append(",").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("Please click the link below to activate your account.").append(System.lineSeparator());
        sb.append(verificationLink).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("If you did not create an account, please ignore this e-mail.");
        return sb.toString();
    }

    public static String getVerificationMailSubject(){
        StringBuilder sb = new StringBuilder();
        sb.append("Verify and activate your YuumYuum account.");
        return sb.toString();
    }

    public static String getConfirmationMailBody(String username){
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(username).append(", ").append("we are so glad you decided to try YuumYuum.");
        sb.append(System.lineSeparator());
        sb.append("Some text!... TO_DO");
        return sb.toString();
    }

    public static String getConfirmationMailSubject(String username){
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(", thank you for Signing up for YuumYuum");
        return sb.toString();
    }

    public static String getForgotPasswordMailBody(){
        StringBuilder sb = new StringBuilder();
        sb.append("Some-Description. + link change password, etc");
        return sb.toString();
    }

    public static String getForgotPasswordMailSubject(){
        StringBuilder sb = new StringBuilder();
        sb.append("Forgot password email.");
        return sb.toString();
    }

}
