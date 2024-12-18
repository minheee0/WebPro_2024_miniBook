<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입</title>
  <!-- 외부 CSS 연결 -->
  <link rel="stylesheet" href="css/login/signup.css">
</head>
<body>
  <div class="login-container">
    <!-- 회원가입 폼 -->
    <form class="login-form" method="post" action="<%= request.getContextPath() %>/register.do">
      <img class="m-i-n-i-i-logo" src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고 이미지" />

      <!-- 아이디 입력 -->
      <div class="form-group">
        <input type="text" id="id" name="id" placeholder="아이디 입력" required>
      </div>

      <!-- 비밀번호 입력 -->
      <div class="form-group">
        <input type="password" id="password" name="password" placeholder="비밀번호 입력" required>
      </div>

      <!-- 이름 입력 -->
      <div class="form-group">
        <input type="text" id="name" name="name" placeholder="닉네임 입력" required>
      </div>

      <!-- 이메일 입력 -->
      <div class="form-group">
        <input type="email" id="email" name="email" placeholder="이메일 입력" required>
      </div>

      <div class="form-group radio-group">
  <label>
    <input type="radio" name="user_type" value="1" required> 관리자
  </label>
  <label>
    <input type="radio" name="user_type" value="2" required> 회원
  </label>
</div>


      <!-- 회원가입 버튼 -->
      <div class="form-group">
        <button type="submit" class="login-button">회원가입</button>
      </div>

      <!-- 로그인 링크 -->
      <div class="button-group">
        <button type="button" class="secondary-button" onclick="location.href='<%=request.getContextPath()%>/LoginForm.jsp'">로그인 페이지</button>
      </div>
    </form>
  </div>
</body>
</html>
