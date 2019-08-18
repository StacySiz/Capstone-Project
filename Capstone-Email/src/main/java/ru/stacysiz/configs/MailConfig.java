//package ru.stacysiz.configs;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfig {
//
//    @Value("${spring.mail.host}")
//    private String host;
//
//    @Value("${spring.mail.port}")
//    private int port;
//
//    @Bean
//    public JavaMailSender javaMailSender(){
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost(host);
//        javaMailSender.setPort(port);
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.username",)
//        javaMailSender.setJavaMailProperties(properties);
//
//        return javaMailSender;
//    }
//}
