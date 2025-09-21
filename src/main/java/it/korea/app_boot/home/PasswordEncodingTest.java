package it.korea.app_boot.home;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodingTest {
    //블럭은 shift + alt + A
    // 여러줄 주석  ctrl(cmd) + /

    /* public static void main(String[] args) {
        
         PasswordEncoder encoder = new BCryptPasswordEncoder();
         String encodePasswd = encoder.encode("1234");
         String passwd = "1234";

         System.out.println(encodePasswd);
         System.out.println( encoder.matches(passwd, encodePasswd));

    } */
}
