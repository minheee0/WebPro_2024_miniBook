<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="member.MemberDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="../css/admin/EditMember.css">
</head>
<body>
    <h1>회원 정보 수정</h1>
    <%
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if (member == null) {
            out.println("<p>수정할 회원 정보를 불러올 수 없습니다.</p>");
            return;
        }
    %>
    <form action="<%= request.getContextPath() %>/admin/admin" method="post">
        <!-- 숨김 필드로 회원 ID 전달 -->
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= member.getId() %>">

        <!-- 이름 -->
        <label for="name">이름</label>
        <input type="text" id="name" name="name" value="<%= member.getName() %>" required>

        <!-- 이메일 -->
        <label for="email">이메일</label>
        <input type="email" id="email" name="email" value="<%= member.getEmail() %>" required>

        <!-- 비밀번호 -->
        <label for="password">비밀번호</label>
        <input type="text" id="password" name="password" value="<%= member.getPassword() %>" required>
        
        <!-- 수정 버튼 -->
        <button type="submit">수정하기</button>
    </form>
    <div class="back-to-list">
        <a href="<%= request.getContextPath() %>/admin/admin?section=members">회원 목록으로 돌아가기</a>
    </div>
</body>
</html>