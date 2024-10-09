<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>회원가입</title>
<link rel="stylesheet" href="./resources/css/signup.css">
<script src="./resources/js/core/jquery-3.7.1.min.js"></script>
<script>
    $(document).ready(function() {
        let timer; // 타이머 변수 추가
        
        function isValidUsername(username) {
            const usernamePattern = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{5,10}$/; // 영문자와 숫자가 모두 포함된 5자 이상 10자 이하
            return usernamePattern.test(username);
        }
    	// 이메일 형식 정규 표현식
        function isValidEmail(email) {
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; 
            return emailPattern.test(email);
        }

        // 이메일 입력 필드의 input 이벤트 리스너
        $("#email").on("input", function() {
            const email = $(this).val().trim();

            // 이메일 형식 검증
            if (isValidEmail(email)) {
                $("#requestCodeBtn").prop("disabled", false); // 이메일 형식이 올바르면 인증 코드 발송 버튼 활성화
            } else {
                $("#requestCodeBtn").prop("disabled", true); // 이메일 형식이 올바르지 않으면 비활성화
            }

            // 이메일 필드가 비어있으면 인증 코드 발송 버튼 비활성화
            if (email === "") {
                $("#requestCodeBtn").prop("disabled", true); // 이메일이 비어있으면 버튼 비활성화
                $("#requestCodeBtn").text("인증 코드 발송"); // 버튼 텍스트 초기화
                $("#requestCodeBtn").css("cursor", "pointer"); // 커서 스타일 변경
                clearInterval(timer); // 타이머 정리
            } else {
                // 이메일이 비어있지 않은 경우 버튼 상태 업데이트
                $("#requestCodeBtn").text("인증 코드 발송"); // 버튼 텍스트 초기화
                $("#requestCodeBtn").css("cursor", "pointer"); // 커서 스타일 변경
                clearInterval(timer); // 타이머 정리
                $("#requestCodeBtn").prop("disabled", false); // 버튼 활성화
                $("#code").val("").prop("disabled", true); // 인증 코드 입력 필드 비활성화
            }
        });

        // 인증 코드 요청 함수
        $("#requestCodeBtn").click(function() {
            var email = $("#email").val();

            // 로딩 스피너 표시
            $("#spinnerContainer").show(); // 여기서 스피너를 보여줍니다.

            $.ajax({
                url: '/sendVerificationCode',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(email),
                success: function(response) {
                    if (response.message === "인증 코드가 이메일로 전송되었습니다!") {
                        showSuccessAlert("요청성공!", "입력하신 이메일에 인증코드를 발송했습니다.");
                        clearInterval(timer);
                        startTimer(300); // 5분 타이머 시작

                        $("#requestCodeBtn").prop("disabled", true);
                        $("#code").prop("disabled", false);
                    } else {
                        showErrorAlert("이미 등록한 이메일 입니다. 다른 이메일을 입력해주세요.");
                    }
                },
                error: function(xhr, status, error) {
                    showErrorAlert("입력하신 이메일을 확인후 다시 시도해 주세요." + error);
                },
                complete: function() {
                    // 로딩 스피너 숨기기
                    $("#spinnerContainer").hide(); // 여기서 스피너를 숨깁니다.
                }
            });
        });

        // 인증 코드 확인 함수
        $("#verifyCodeBtn").click(function() {
            var email = $("#email").val();
            var code = $("#code").val();

            console.log(code);
            $.ajax({
                url: '/verifyCode',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    email: email,
                    code: code
                }),
                success: function(response) {
                    if ("인증이 완료되었습니다!" === response.message) {
                        showSuccessAlert("요청성공!", response.message);
                        $("#email").prop("readonly", true); // 이메일 필드 비활성화
                        $("#requestCodeBtn").prop("disabled", true); // 인증 코드 요청 버튼 비활성화
                        $("#code").prop("disabled", true); // 인증 코드 입력 필드 비활성화
                        $("#verifyCodeBtn").prop("disabled", true); // 인증 코드 확인 버튼 비활성화
                    }
                },
                error: function(xhr, status, error) {
                    // 오류 메시지 표시
                    showErrorAlert(xhr.responseJSON.message);
                }
            });
        });
        
        // 아이디 길이에 따른 버튼 활성화
	    $("#username").on("input", function() {
		    const userid = $(this).val().trim();
		    if (isValidUsername(userid)) {
		        $("#userIdBtn").prop("disabled", false);
		        $(this).css("border-color", "#bfb3f2"); // 유효한 경우 색상 변경
		    } else {
		        $("#userIdBtn").prop("disabled", true);
		        $(this).css("border-color", "red"); // 유효하지 않은 경우 색상 변경
		    }
		});

        // 비밀번호 입력 시 비밀번호 확인 필드 활성화
        $("#password").on("input", function() {
            var password = $(this).val().trim();
            if (password) {
                $("#confirm-password").prop("disabled", false); // 비밀번호 입력 시 활성화
            } else {
                $("#confirm-password").prop("disabled", true); // 비밀번호가 없으면 비활성화
                $("#confirm-password").val(""); // 비밀번호 확인 필드 초기화
                $("#password-message").text(""); // 메시지 초기화
            }
        });

        // 모든 입력값이 채워졌는지 확인하는 함수
        function checkFormCompletion() {
		    const username = $("#username").val().trim();
		    const name = $("#name").val().trim();
		    const password = $("#password").val().trim();
		    const confirmPassword = $("#confirm-password").val().trim();
		    const email = $("#email").val().trim();
		    const code = $("#code").val().trim();
		    const phone = $("#phone").val().trim();
		
		    // 모든 입력값이 비어있지 않으면 회원가입 버튼 활성화
		    if (isValidUsername(username) && name && password && confirmPassword && email && code && phone && password === confirmPassword) {
		        $(".signup-button").prop("disabled", false); // 회원가입 버튼 활성화
		    } else {
		        $(".signup-button").prop("disabled", true); // 회원가입 버튼 비활성화
		    }
		}
        

        // 아이디 중복 검사 상태를 저장할 변수
        let isUsernameAvailable = false;
        
        
     	// 인증 코드 확인 함수
        $("#userIdBtn").click(function() {
            var member_id = $("#username").val().trim();
			
            $.ajax({
                url: '/checkUserId',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    member_id: member_id
                }),
                success: function(response) {
                    if ("사용가능한 아이디 입니다!" === response.message) {
                        showSuccessAlert("요청성공!", response.message); // 사용가능한 아이디 입니다.
                        isUsernameAvailable = true; // 상태 업데이트
                   		$("#userIdBtn").prop("disabled",true);
                   		$("#username").prop("readonly",true);
                    }
                    else {
                    	showErrorAlert(response.message); // 중복된 아이디 입니다. 
                    	isUsernameAvailable = false; // 상태 업데이트
                    }
                    checkFormCompletion(); // 중복 검사 후 폼 체크
                },
                error: function(xhr, status, error) {
                    // 오류 메시지 표시
                    showErrorAlert(xhr.responseJSON.message);
                }
            });
        });

        // 비밀번호 일치 여부 확인
        $("#confirm-password").on("input", function() {
            var password = $("#password").val();
            var confirmPassword = $(this).val();
            var message = $("#password-message"); // 메시지를 표시할 span 선택

            if (password !== confirmPassword) {
                $(this).css("border-color", "red"); // 일치하지 않으면 빨간색 테두리
                message.text("비밀번호가 일치하지 않습니다."); // 메시지 설정
                message.css("color", "red"); // 메시지 색상 변경
            } else {
                $(this).css("border-color", "#bfb3f2"); // 일치하면 초록색 테두리
                message.text("비밀번호가 일치합니다."); // 메시지 설정
                message.css("color", "#bfb3f2"); // 메시지 색상 변경
            }

            checkFormCompletion(); // 비밀번호 확인 후 폼 체크
        });

        // 각 입력 필드에 input 이벤트 리스너 추가
        $("#username, #name, #password, #confirm-password, #email, #code, #phone").on("input", function() {
            checkFormCompletion();
        });

        // 인증 코드 입력 필드의 input 이벤트 리스너
        $("#code").on("input", function() {
            const code = $(this).val().trim();

            // 인증 코드가 6자리가 되면 비활성화 해제
            if (code.length === 6) {
                $("#verifyCodeBtn").prop("disabled", false); //
							} else {
								$("#verifyCodeBtn").prop("disabled", true); // 인증 코드 입력 필드 비활성화
							}

							checkFormCompletion(); // 다른 필드와 함께 폼 체크
						});

						// 비밀번호 일치 여부 확인
						$("#confirm-password").on("input", function() {
							var password = $("#password").val();
							var confirmPassword = $(this).val();
							var message = $("#password-message"); // 메시지를 표시할 span 선택

							if (password !== confirmPassword) {
								$(this).css("border-color", "red"); // 일치하지 않으면 빨간색 테두리
								message.text("비밀번호가 일치하지 않습니다."); // 메시지 설정
								message.css("color", "red"); // 메시지 색상 변경
							} else {
								$(this).css("border-color", "#bfb3f2"); // 일치하면 초록색 테두리
								message.text("비밀번호가 일치합니다."); // 메시지 설정
								message.css("color", "#bfb3f2"); // 메시지 색상 변경
							}
						});
						 // 타이머 시작 함수
					  function startTimer(duration) {
					      let timerDuration = duration, minutes, seconds;
					      $("#requestCodeBtn").prop("disabled", true).css("cursor", "default"); // 버튼 비활성화 및 커서 스타일 변경
					
					      timer = setInterval(function() {
					          minutes = parseInt(timerDuration / 60, 10);
					          seconds = parseInt(timerDuration % 60, 10);
					          minutes = minutes < 10 ? "0" + minutes : minutes;
					          seconds = seconds < 10 ? "0" + seconds : seconds;
					
					          $("#requestCodeBtn").text(minutes + ":" + seconds); // 버튼 텍스트 업데이트
					
					          if (--timerDuration < 0) {
					              clearInterval(timer);
					              $("#requestCodeBtn").prop("disabled", false).css("cursor", "pointer").text("인증 코드 발송"); // 버튼 활성화
					          }
					      }, 1000);
					  }
					 
					  
					  function showSuccessAlert(message, detail) {
						    swal(message, detail, {
						        icon: "success",
						        buttons: {
						            confirm: {
						                className: "btn btn-success",
						            },
						        },
						    });
						}

						function showErrorAlert(message) {
						    swal("오류 발생!", message, {
						        icon: "error",
						        buttons: {
						            confirm: {
						                className: "btn btn-danger",
						            },
						        },
						    });
						}
					  
						 
					  
					  
					});
</script>
</head>
<body>
<div id="spinnerContainer" class="spinner-container" style="display: none;">
    <div id="loadingSpinner" class="spinner"></div>
</div>
	<div class="signup-container">
		<h2 class="form-title">회원가입</h2>

		<p class="separator"></p>
		<form action="signup" class="signup-form" method="post">
			<div class="input-wrapper">
				<label for="username" class="input-label">아이디</label> <input
					type="text" id="username" placeholder="영문자와 숫자를 포함하여 5자 이상 10자 이하로 입력해주세요." class="input-field"
					required name="member_id" />
						<button type="button" id="userIdBtn"
					class="send-userId-button" disabled >중복 조회</button>
			</div>
			<div class="input-wrapper">
				<label for="name" class="input-label">이름</label> <input type="text"
					id="name" placeholder="홍길동" class="input-field" required
					name="member_name" />
			</div>
			<div class="input-wrapper">
				<label for="password" class="input-label">비밀번호</label> <input
					type="password" id="password" placeholder="Password"
					class="input-field" required name="member_pw" />
			</div>
			<div class="input-wrapper">
				<label for="confirm-password" class="input-label">비밀번호 확인</label> <input
					type="password" id="confirm-password" placeholder="Password"
					class="input-field" required disabled /> <span
					id="password-message" class="password-message"></span>
				<!-- 메시지를 표시할 span 추가 -->
			</div>
			<div class="input-wrapper">
				<label for="email" class="input-label">이메일</label> 
				<input
					type="email" id="email" placeholder="itwill@itwillbs.com"
					class="input-field"  name="member_email" required />
				<button type="button" id="requestCodeBtn"
					class="send-verification-button" disabled>인증 코드 발송</button>
			</div>
			<div class="input-wrapper">
				<label for="auth-code" class="input-label">인증 코드</label> <input
					type="text" id="code" placeholder="!@#$%^&" class="input-field"
					required disabled />
				<button type="button" id="verifyCodeBtn"
					class="send-verification-button auth" disabled>코드 인증</button>
			</div>
			<div class="input-wrapper">
				<label for="phone" class="input-label">전화번호</label> <input
					type="tel" id="phone" placeholder="010-1234-5678"
					class="input-field" required name="member_tel" />
			</div>
			<button class="signup-button" disabled>회원가입</button>
		</form>
		

		<p class="signup-text">
			회원이신가요? <a href="/login">로그인</a>
		</p>
	</div>
	


    <!-- Sweet Alert -->
    <script src="./resources/js/plugin/sweetalert/sweetalert.min.js"></script>
  
</body>
</html>
