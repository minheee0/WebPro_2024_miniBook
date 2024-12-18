<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Book" %>
<%@ page import="model.ReviewDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html>
<head>
    <title>검색 결과</title>
    <link rel="stylesheet" type="text/css" href="css/review/review.css"> <!-- CSS 파일 연결 -->
</head>
<body>
    <!-- 디버깅 로그 -->
    <%
        Book book = (Book) request.getAttribute("book");
        List<ReviewDTO> reviews = (List<ReviewDTO>) request.getAttribute("reviews");
        System.out.println("Result.jsp loaded");
        System.out.println("Book: " + book);
        System.out.println("Reviews count: " + (reviews != null ? reviews.size() : "null"));
    %>

    <!-- 로고 이미지 -->
    <a href="index.jsp">
        <img src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고" class="logo">
    </a>

    <div class="container">
        <!-- 제목 -->
        <h2>검색 결과</h2>

        <!-- 다시 검색 버튼 -->
        <a href="<%= request.getContextPath() %>/categories" class="button">다시검색</a>
        <hr>

        <%
            if (book != null) { // book이 null이 아닐 때만 결과 출력
        %>

        <!-- 리뷰 목록과 도서 정보를 나누기 위한 컨테이너 -->
        <div class="review-container">
            <!-- 왼쪽 박스 (도서 정보) -->
            <div class="left-box">
                <!-- 도서 정보 -->
                <img src="<%= book.getCover() %>" alt="Book Cover" class="book-cover">
                <p><strong>제목:</strong> <%= book.getTitle() %></p>
                <p><strong>저자:</strong> <%= book.getAuthor() %></p>
                <p><strong>출판사:</strong> <%= book.getPublisher() %></p>
                <p><strong>출판년도:</strong> <%= book.getPubDate() %></p>
                <a href="addReview.jsp?title=<%= URLEncoder.encode(book.getTitle(), "UTF-8") %>" class="review-link">리뷰 쓰기</a>
            </div>

            <!-- 오른쪽 박스 (리뷰 목록) -->
            <div class="right-box">
                <h3>리뷰 목록</h3>
                <ul>
                    <%
                        if (reviews != null && !reviews.isEmpty()) {
                            for (ReviewDTO review : reviews) {
                                String formattedDate = review.getCreatedAt()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    %>
                                <li>
                                    <strong><%= review.getUserId() %>님</strong> - 평점: <%= review.getRating() %>/5<br>
                                    <p><%= review.getReviewContent() %></p>
                                    <small>작성일: <%= formattedDate %></small>
                                </li>
                    <%
                            }
                        } else {
                    %>
                            <p>리뷰가 없습니다. 첫 번째 리뷰를 작성해보세요!</p>
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>

        <% 
            } else { 
        %>
            <p>도서 정보를 가져올 수 없습니다. 다시 시도해주세요.</p>
        <% } %>
    </div>
</body>
</html>
