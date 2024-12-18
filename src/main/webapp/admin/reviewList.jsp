<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.ReviewDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 목록</title>
    <link rel="stylesheet" href="../css/admin/reviewList.css">

</head>
<body>
   <div class="back-to-admin">
        <a href="<%= request.getContextPath() %>/admin/admin.jsp">관리자 페이지로 가기</a>
    </div>
    <h1>리뷰 목록</h1>
    <%
        List<ReviewDTO> reviews = (List<ReviewDTO>) request.getAttribute("reviews");
        if (reviews != null && !reviews.isEmpty()) {
    %>
    <table>
        <thead>
            <tr>
               <th>ID</th>
                <th>도서명</th>
                <th>작성자</th>
                <th>평점</th>
                <th>리뷰 내용</th>
                <th>작성일</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <% for (ReviewDTO review : reviews) { %>
            <tr>
               <td><%= review.getReviewId() %></td>
                <td><%= review.getBookTitle() %></td>
                <td><%= review.getUserId() %></td>
                <td><%= review.getRating() %>/5</td>
                <td><%= review.getReviewContent() %></td>
                <td><%= review.getCreatedAt() %></td>
                <td>
                <a href="<%= request.getContextPath() %>/admin/editReview?reviewId=<%= review.getReviewId() %>">
                <button type="button">수정</button>
               </a>

            </td>

                <td>
                    <!-- 삭제 버튼 -->
                    <form action="<%= request.getContextPath() %>/admin/reviewList" method="post">
                   <input type="hidden" name="action" value="delete">
                   <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                   <button type="submit">삭제</button>
               </form>

                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <%
        } else {
    %>
    <p>리뷰가 없습니다.</p>
    <%
        }
    %>
</body>
</html>
