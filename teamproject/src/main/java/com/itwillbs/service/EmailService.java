package com.itwillbs.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.itwillbs.mail.VerificationCodeGenerator;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	// 인증 코드를 저장할 맵
    private ConcurrentHashMap<String, VerificationEntry> verificationCodes = new ConcurrentHashMap<>();

    // 스케줄러 서비스
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public void sendVerificationCode(String to) {
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();
        long timestamp = System.currentTimeMillis();// 현재 시간// 인증 코드와 함께 생성 시간을 저장
        verificationCodes.put(to, new VerificationEntry(verificationCode, timestamp));

        String subject = "이메일 인증 코드";
//       여기서 태그를 꾸밀 수 있음.
        String body = "다음 인증 코드를 입력하세요: " + verificationCode;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        
        // 5분 후에 인증 코드를 삭제하는 작업 예약
        scheduler.schedule(() -> verificationCodes.remove(to), 6, TimeUnit.MINUTES);
    }

    public boolean verifyCode(String email, String code) {
        VerificationEntry entry = verificationCodes.get(email);
        if (entry != null) {
// 5분(300000ms) 이내에 입력된 코드인지 확인
            if (System.currentTimeMillis() - entry.getTimestamp() <= TimeUnit.MINUTES.toMillis(5) &&
                entry.getCode().equals(code)) {
                return true;// 인증 성공
            }
        }
        return false;// 인증 실패
    }

    private static class VerificationEntry {
        private String code;
        private long timestamp;

        public VerificationEntry(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }

        public String getCode() {
            return code;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}