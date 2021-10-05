package com.example.testlocal.controller;

import com.example.testlocal.domain.dto.UserDTO2;
import com.example.testlocal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SignupController {

    private final JavaMailSender javaMailSender;
    private final UserService userService;

    @PostMapping("/sendSejongEmail")
    public void sendSejongEmail(@RequestBody Map<String, String> map, HttpServletRequest request) throws MessagingException {

        String email = map.get("email") + "@sju.ac.kr";

        // db에서 이메일 중복 체크 해야함.

        // 키값 생성
        String authCode = getAuthCode();

        String content = "<h4>안녕하세요.</h4><h4>Sejong Coding Helper입니다.</h4>" + "<h4>세종대학교 이메일 인증을 위해서 아래 인증 코드를 입력해주세요.</h4>" +
                "<h2>인증 코드 : " + "<b><u>"+authCode+ "</u></b><h2>"+ "<h4>감사합니다.</h4>";

        // 메일 보내기
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom("Sejong Coding Helper<sjhelper10@gmail.com>");
        message.setSubject("Sejong Coding Helper 회원가입 인증 메일");
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        message.setText(content,"UTF-8","html");
        message.setSentDate(new Date());

        javaMailSender.send(message);

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*10);  //10분
        session.setAttribute("authCode",authCode);
    }

    @PostMapping("checkEmailAuthCode")
    public String checkEmailAuthCode(@RequestBody Map<String, String> map,HttpServletRequest request){
        HttpSession session = request.getSession();
        String authCode =(String)session.getAttribute("authCode");
        String inputedAuthCode = map.get("authCode");
        System.out.println(authCode + "," +inputedAuthCode);

        if(authCode.equals(inputedAuthCode))
            return "accepted";
        else
            return "fail";

    }

    @PostMapping("/completeUserSignup")
    public void completeUserSignup(@RequestBody Map<String, String> map){

        // db에 저장하는 구문
        userService.signUp(new UserDTO2(map.get("studentNumber"), map.get("pwd"), map.get("name"),map.get("email")));
    }

    @PostMapping("/checkIdOverlap")
    public void checkIdOverlap(@RequestBody Map<String, String> map){

    }


    //인증코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }

}
