<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ReviewDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 수정</title>
    <link rel="stylesheet" href="../css/admin/editReview.css">
   
</head>
<body>
    <h1>리뷰 수정</h1>
    <%
        ReviewDTO review = (ReviewDTO) request.getAttribute("review");
        if (review == null) {
    %>
        <div style="text-align: center; margin-top: 20px; color: red;">
            <p>리뷰 정보를 불러오는 데 실패했습니다. 다시 시도해 주세요.</p>
            <a href="<%= request.getContextPath() %>/myPage">마이페이지로 돌아가기</a>
        </div>
    <% return; } %>
    <form action="<%= request.getContextPath() %>/admin/editReview" method="post">
    <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">

    <label for="bookTitle">도서명:</label>
    <input type="text" id="bookTitle" name="bookTitle" value="<%= review.getBookTitle() %>" readonly>


    <label for="rating">평점:</label>
    <select id="rating" name="rating" required>
        <% for (int i = 1; i <= 5; i++) { %>
            <option value="<%= i %>" <%= i == review.getRating() ? "selected" : "" %>><%= i %>점</option>
        <% } %>
    </select>

    <label for="reviewContent">리뷰 내용:</label>
    <textarea id="reviewContent" name="reviewContent" rows="5" required><%= review.getReviewContent() %></textarea>

    <button type="submit">수정하기</button>
</form>



    <script>
        document.querySelector('form').addEventListener('submit', function(e) {
            const rating = document.getElementById('rating').value;
            if (!rating) {
                alert('평점을 선택하세요.');
                e.preventDefault();
            }
        });
    </script>
</body>
</html>
