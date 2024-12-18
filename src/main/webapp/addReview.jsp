<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>리뷰 작성</title>
    <link rel="stylesheet" type="text/css" href="css/review/addReview.css">
    
</head>
<body>
    <%
        // 세션에서 로그인한 사용자의 아이디 가져오기
        String id = (String) session.getAttribute("idKey");

        // 로그인하지 않은 사용자가 접근할 경우 로그인 페이지로 리디렉션
        if (id == null) {
            response.sendRedirect("loginReviewForm.jsp");
            return;
        }

        // 도서 제목 가져오기 (검색 결과에서 전달)
        String bookTitle = request.getParameter("title");
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            bookTitle = "도서 정보 없음"; // 기본값 설정
        }
    %>

    <!-- 로고 -->
    <div class="logo-container">
        <a href="index.jsp">
            <img src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고" class="logo">
        </a>
    </div>

    <h1>리뷰 작성</h1>

    <div class="form-container">
        <form id="reviewForm" action="submitReview" method="post">
            <!-- 사용자 아이디는 숨겨진 필드로 전달 -->
            <input type="hidden" name="userId" value="<%= id %>">
            <input type="hidden" name="bookTitle" value="<%= bookTitle %>">

            <label>도서 제목:</label>
            <p><%= bookTitle %></p>

            <label for="rating">평점:</label>
            <select name="rating" id="rating" required>
                <option value="" disabled selected>선택하세요</option>
                <option value="1">1점</option>
                <option value="2">2점</option>
                <option value="3">3점</option>
                <option value="4">4점</option>
                <option value="5">5점</option>
            </select>

            <label for="reviewContent">리뷰 내용:</label>
            <textarea id="reviewContent" name="reviewContent" rows="5" required></textarea>

            <button type="submit" class="submit-button">리뷰 제출</button>
        </form>
    </div>

    <script>
        document.getElementById('reviewForm').addEventListener('submit', function(e) {
            const rating = document.getElementById('rating').value;
            const reviewContent = document.getElementById('reviewContent').value.trim();

            if (!rating) {
                alert('평점을 선택하세요.');
                e.preventDefault();
                return;
            }

            if (!reviewContent) {
                alert('리뷰 내용을 작성하세요.');
                e.preventDefault();
                return;
            }
        });
    </script>
</body>
</html>
