<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ReviewDTO" %>
<%@ page import="java.time.LocalDateTime" %>

<%
    // 요청 파라미터에서 데이터 추출
    String bookTitle = request.getParameter("bookTitle");
    String reviewContent = request.getParameter("reviewContent");
    String ratingStr = request.getParameter("rating");
    String reviewIdStr = request.getParameter("reviewId");

    int rating = 0;
    int reviewId = 0;

    // 평점 및 리뷰 ID 검증
    try {
        if (ratingStr != null && !ratingStr.isEmpty()) {
            rating = Integer.parseInt(ratingStr);
        }
        if (reviewIdStr != null && !reviewIdStr.isEmpty()) {
            reviewId = Integer.parseInt(reviewIdStr);
        }
    } catch (NumberFormatException e) {
        System.out.println("Invalid parameter value.");
    }

    // 세션에서 사용자 ID 확인
    String userId = (String) session.getAttribute("idKey");
    if (userId == null) {
        response.sendRedirect("loginPage.jsp");
        return;
    }

    // ReviewDTO 객체 생성
    ReviewDTO review = new ReviewDTO(reviewId, bookTitle, userId, rating, reviewContent, LocalDateTime.now());
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>리뷰 수정</title>
    <link rel="stylesheet" href="css/review/updateReview.css"/>

</head>
<body>
    <div class="container">
        <h1>리뷰 수정</h1>

        <!-- 리뷰 수정 폼 -->
        <form id="reviewForm" action="updateReview" method="post">
    <!-- 숨김 필드 -->
    <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
    <input type="hidden" name="userId" value="<%= review.getUserId() %>">

    <!-- 도서명 -->
    <label for="bookTitle" class="form-label">도서명:</label>
    <input type="text" id="bookTitle" name="bookTitle" class="form-input" value="<%= review.getBookTitle() %>" readonly>

    <!-- 평점 -->
    <label for="rating" class="form-label">평점:</label>
    <select id="rating" name="rating" class="form-select" required>
        <option value="" disabled <%= review.getRating() == 0 ? "selected" : "" %>>선택하세요</option>
        <% for (int i = 1; i <= 5; i++) { %>
            <option value="<%= i %>" <%= i == review.getRating() ? "selected" : "" %>><%= i %>점</option>
        <% } %>
    </select>

    <!-- 리뷰 내용 -->
    <label for="reviewContent" class="form-label">리뷰 내용:</label>
    <textarea id="reviewContent" name="reviewContent" rows="5" class="form-textarea" required><%= review.getReviewContent() %></textarea>

    <!-- 제출 버튼 -->
    <button type="submit" class="submit-button">수정 완료</button>
</form>

    </div>

    <script>
        // 폼 유효성 검사
        document.getElementById('reviewForm').addEventListener('submit', function(event) {
            const rating = document.getElementById('rating').value;
            if (!rating || isNaN(rating) || rating < 1 || rating > 5) {
                alert('평점은 1점에서 5점 사이여야 합니다.');
                event.preventDefault();
            }
        });
    </script>
</body>
</html>
