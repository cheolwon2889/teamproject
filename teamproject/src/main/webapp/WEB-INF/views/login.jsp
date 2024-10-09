<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>로그인</title>
    <link rel="stylesheet" href="./resources/css/login.css">
  </head>
  <body>
    <div class="login-container">
      <h2 class="form-title">로그인</h2>
      <div class="social-login">
        <button class="social-button" onclick=" location.href='${naver_url}'; " >
          <img
            src="./resources/img/btnG.png"
            alt="Naver"
            class="social-icon"
          />
        </button>
      </div>

      <p class="separator"><span>or</span></p>
      <form action="#" class="login-form">
        <div class="input-wrapper">
          <input type="id" placeholder="Id" class="input-field" required />
        </div>
        <div class="input-wrapper">
          <input type="password" placeholder="Password" class="input-field" required />
        </div>

        <button class="login-button">로그인</button>
      </form>

      <p class="signup-text">회원이 아니신가요? <a href="/signup">회원가입</a></p>
    </div>
  </body>
</html>
