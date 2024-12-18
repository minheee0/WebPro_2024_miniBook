<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html>
<head>

<title>정보 수정</title>
<meta charset="UTF-8">

<!-- 추가: UTF-8 인코딩 설정 -->
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPage/updateInfo.css" />
 <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" />
 
</head>

<body>
    <div class="container">
        <h2>정보 수정</h2>
        
        <c:if test="${not empty sessionScope.idKey}">

            <form action="updateInfo" method="post">
                <label for="name">이름: </label>
                <input type="text" id="name" name="name" value="${sessionScope.name}" required />
                <label for="email">이메일: </label>
                <input type="email" id="email" name="email" value="${sessionScope.email}" required />
                <label for="newPassword">새 비밀번호: </label>
                <input type="password" id="newPassword" name="newPassword" />
                <label for="confirmPassword">새 비밀번호 확인: </label>
                <input type="password" id="confirmPassword" name="confirmPassword" />
                <input type="submit" value="수정하기" />
            </form>
            
        </c:if>
        
        <c:if test="${empty sessionScope.idKey}">
            <p>로그인 후 이용 가능합니다.</p>
            <a href="LoginForm.jsp">로그인</a>
            
        </c:if>
    </div>
</body>
</html>