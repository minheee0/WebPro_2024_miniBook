<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>도서 리뷰 검색</title>
    <link rel="stylesheet" type="text/css" href="css/bookSearch/bookSearch.css">
</head>
<body>

	
    <div class="container">
    	<!-- 로고 및 카테고리 메뉴 -->
	
			<a href="<%= request.getContextPath() %>">
				<img src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고" class="logo">
			</a>
		
    
        <h2>도서명으로 리뷰검색</h2>
        <form action="search" method="get">
            <input type="text" id="query" name="query" placeholder="검색어를 입력하세요" />
            <button type="submit">검색</button>
        </form>
        <p style="color: red;">
            <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
        </p>
        <p class="info">※ 도서 제목을 입력하세요. 상세 검색 조건을 지정해야 합니다.</p>
    </div>
</body>
</html>
