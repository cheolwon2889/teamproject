package com.itwillbs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendVerificationCode")
    public String sendVerificationCode(@RequestBody String email) {
        emailService.sendVerificationCode(email);
        return "인증 코드가 이메일로 전송되었습니다!";
    }

    @PostMapping("/verifyCode")
    public String verifyCode(@RequestBody VerificationRequest request) {
        boolean isValid = emailService.verifyCode(request.getEmail(), request.getCode());
        return isValid ? "인증이 완료되었습니다!" : "인증 코드가 잘못되었거나 만료되었습니다.";
    }

// 인증 코드 저장을 위한 DTO
    public static class VerificationRequest {
        private String email;
        private String code;

// Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }
}