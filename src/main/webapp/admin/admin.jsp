<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
    <link rel="stylesheet" type="text/css" href="../css/admin/admin.css">
</head>
<body>
    <!-- 전체 내용을 감싸는 컨테이너 -->
    <div class="container">
        <div class="header">
            <!-- 로고 이미지 삽입 -->
            <a href="<%= request.getContextPath() %>/index.jsp">
                <img src="<%= request.getContextPath() %>/img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고">
            </a>
            <h1>관리자 페이지</h1>
        </div>
        <div class="nav">
            <!-- 내비게이션 메뉴 -->
            <a href="/team444/admin/admin?section=members">회원 관리</a>
            <a href="<%= request.getContextPath() %>/admin/reviewList">리뷰 목록</a>
        </div>
        <div>
            <% 
                String section = request.getParameter("section");
                if ("members".equals(section)) { 
            %>
                <!-- 회원 관리 iframe -->
                <iframe src="admin/memberList.jsp"></iframe>
            <% 
                } else if ("reviews".equals(section)) { 
            %>
                <!-- 리뷰 목록 iframe -->
                <iframe src="admin/reviewList.jsp"></iframe>
            <% 
                } else { 
            %>
                <!-- 기본 안내 메시지 -->
                <h2>상단 메뉴에서 관리할 항목을 선택하세요.</h2>
            <% 
                } 
            %>
        </div>
    </div>
</body>
</html>
