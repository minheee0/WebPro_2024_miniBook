<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>loginFail Page</title>
  <!-- 외부 CSS 연결 -->
  <link rel="stylesheet" href="css/login/login.css">
</head>

<body>
  <div class="login-container">
    <!-- 로그인 폼 -->
    <form class="login-form" method="post" action="<%= request.getContextPath() %>/login.do">
      
      <a href="index.jsp"><img class="m-i-n-i-i-logo" src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="Logo" /> </a>

      <!-- 로그인 후 이용 가능합니다 문구 -->
      <div class="login-info">
        <p>아이디 혹은 비밀번호를 다시 입력해주세요</p>
      </div>

      <!-- 아이디 입력 -->
      <div class="form-group">
        <input type="text" id="id" name="id" placeholder="아이디 입력" required>
      </div>

      <!-- 비밀번호 입력 -->
      <div class="form-group">
        <input type="password" id="password" name="password" placeholder="비밀번호 입력" required>
      </div>

      <div class="form-group radio-group">
        <label>
          <input type="radio" name="user_type" value="1" required> 관리자
        </label>
        <label>
          <input type="radio" name="user_type" value="2" required> 회원
        </label>
      </div>

      <!-- 회원가입과 정보수정 버튼 -->
      <div class="button-group">
        <button type="button" class="secondary-button" onclick="location.href='SignUpForm.jsp'">회원가입&nbsp;</button> |
        <button type="button" class="secondary-button" onclick="location.href='UpdateForm.jsp'">&nbsp;비밀번호 재설정</button>
      </div>

      <!-- 로그인 버튼 -->
      <div class="form-group">
        <button type="submit" class="login-button">로그인</button>
      </div>
    </form>
  </div>
</body>

</html>
