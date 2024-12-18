<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.ReviewDTO" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    if (request.getAttribute("reviews") == null) {
        response.sendRedirect("myPage");  // 'myPage' 서블릿으로 리디렉트
        return;  // JSP 실행을 중단
    }
%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>마이 페이지</title>
    <link rel="stylesheet" href="css/myPage/myPage.css">
</head>

<body>
    <header class="header">
        <a href="<%= request.getContextPath() %>">
            <img src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고" class="logo">
        </a>
        <h3>마이페이지</h3>
    </header>

    <div class="container">
        <div class="member-info">
            <!-- 프로필 이미지 영역 -->
            <div class="profile-img" onclick="openModal()"></div>
            <!-- 로그인한 사용자 아이디 출력 -->
            <p>환영합니다, <%= session.getAttribute("idKey") %>님!</p>
            <p>회원 등급: 일반</p>
            <!-- 정보 수정 링크 추가 -->
        <a href="updateInfo.jsp"><span class="info-id">정보 수정</span></a>
        </div>

        <div class="review-section">
            <h2>내가 작성한 리뷰 목록</h2>

            <%
                List<ReviewDTO> reviews = (List<ReviewDTO>) request.getAttribute("reviews");
                if (reviews != null && !reviews.isEmpty()) {
            %>

                <p>리뷰 개수: <%= reviews.size() %></p>
                <ul>
                    <% for (ReviewDTO review : reviews) { %>
                        <li>
                            <p><strong>도서 제목:</strong> <%= review.getBookTitle() %></p>
                            <p><strong>평점:</strong> <%= review.getRating() %></p>
                            <p><strong>리뷰 내용:</strong> <%= review.getReviewContent() %></p>
                            <p><strong>작성 날짜:</strong> 
                                <%= review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %>
                            </p>

                            <!-- 수정 버튼 -->
                            <form action="updateReview.jsp" method="get" style="display: inline;">
                                <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                                <input type="hidden" name="bookTitle" value="<%= review.getBookTitle() %>">
                                <input type="hidden" name="reviewContent" value="<%= review.getReviewContent() %>">
                                <input type="hidden" name="rating" value="<%= review.getRating() %>">
                                <button type="submit">수정</button>
                            </form>

                            <!-- 삭제 버튼 -->
                            <form action="deleteReview" method="post" style="display: inline;">
                                <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                                <button type="submit">삭제</button>
                            </form>
                            <hr>
                        </li>
                    <% } %>
                </ul>

            <% } else { %>
                <p>작성한 리뷰가 없습니다.</p>
            <% } %>
        </div>

    </div>

    <footer>
        &copy; 대표이사 : 박민희, 최진솔, 정현오 | 서울특별시 종로구 종로 1 | 사업자등록번호 : 102-81-11670
        <br>© MINII BOOK CENTRE
    </footer>

</body>

</html>
