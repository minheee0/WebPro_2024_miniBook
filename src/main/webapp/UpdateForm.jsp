<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login Page</title>
  <!-- 외부 CSS 연결 -->
  <link rel="stylesheet" href="css/login/login.css">
  
</head>

<body>
  <div class="login-container">
    <!-- 로그인 폼 -->
    <form class="login-form" method="post" action="<%= request.getContextPath() %>/updatePassword.do">
      <a href="index.jsp"><img class="m-i-n-i-i-logo" src="img/MainPage_img/m-i-n-i-i-logo-1.png" />
      </a>
      <!-- 아이디 입력 -->
      <div class="form-group">
        <input type="text" id="id" name="id" placeholder="아이디 입력" required>
      </div>
      
      <!-- 기존 비밀번호 입력 -->
      <div class="form-group">
        <input type="password" id="oldPassword" name="oldPassword" placeholder="기존 비밀번호 입력" required>
      </div>

      <!-- 새 비밀번호 입력 -->
      <div class="form-group">
        <input type="password" id="newPassword" name="newPassword" placeholder="변경할 비밀번호 입력" required>
      </div>

      <!-- 수정하기 버튼 -->
      <div class="form-group">
        <button type="submit" class="login-button">수정하기</button>
      </div>
    </form>
  </div>
</body>



</html>
