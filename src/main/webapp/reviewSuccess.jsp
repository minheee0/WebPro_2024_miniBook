<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 작성 완료</title>
    <link rel="stylesheet" type="text/css" href="css/review/reviewSuccess.css">
</head>
<body>
    <div class="logo-container">
        <a href="index.jsp">
            <img src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고" class="logo">
        </a>
    </div>
    <h1>리뷰 작성이 완료되었습니다!</h1>
    <p>감사합니다! 작성해주신 리뷰는 도서에 반영되었습니다.</p>
    <form action="<%= request.getContextPath() %>/search" method="GET">
        <input type="hidden" name="query" value="<%= request.getAttribute("bookTitle") %>">
        <button type="submit" class="search-button">리뷰 보러 가기</button>
    </form>
</body>
</html>
