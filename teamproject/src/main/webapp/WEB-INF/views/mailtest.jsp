<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>인증 코드 요청</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
// 인증 코드 요청 함수
            $("#requestCodeBtn").click(function() {
                var email = $("#email").val();
                $.ajax({
                    url: '/web/sendVerificationCode',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(email),
                    success: function(response) {
                        alert("메일을 발송하였습니다.");// 성공 메시지 표시
                    },
                    error: function(xhr, status, error) {
                        alert("오류 발생: " + error);// 오류 메시지 표시
                    }
                });
            });

// 인증 코드 확인 함수 kawa640213@naver.com
            $("#verifyCodeBtn").click(function() {
                var email = $("#email").val();
                var code = $("#code").val();
                $.ajax({
                    url: '/web/verifyCode',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ email: email, code: code }),
                    success: function(response) {
                        alert("인증코드가 맞습니다.");// 성공 메시지 표시
                    },
                    error: function(xhr, status, error) {
                        alert("오류 발생: " + error);// 오류 메시지 표시
                    }
                });
            });
        });
    </script>
</head>
<body>
    <h1>인증 코드 요청</h1>
    <label for="email">이메일 주소:</label><br>
    <input type="email" id="email" required><br><br>
    <button id="requestCodeBtn">인증 코드 요청</button>

    <h1>인증 코드 입력</h1>
    <label for="code">인증 코드:</label><br>
    <input type="text" id="code" required><br><br>
    <button id="verifyCodeBtn">인증하기</button>
</body>
</html>