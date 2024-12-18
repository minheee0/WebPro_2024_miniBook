<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="utils.AladinApiService"%>
<%@ page import="model.Book"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>미니문고</title>
<link rel="stylesheet" href="css/MainPage_css/globals.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/MainPage_css/style.css">

</head>
<body>

	<!-- 헤더 -->
	<header>
		<div class="box1">
			<!-- 상단 메뉴 -->
			<div class="top_1">
				<nav id="top_menu">
					<ul>
						<li><a href="SignUpForm.jsp">회원가입</a></li>
						<%
                  String id = (String) session.getAttribute("idKey");
                  if (id != null) {
                  %>
						<li><a href="<%=request.getContextPath()%>/logout.do"><span
								class="logout-id">로그아웃</span></a></li>
						<%
                  } else {
                  %>
						<li><a href="LoginForm.jsp">로그인</a></li>
						<%
                  }
                  %>
						<li><a href="#">회원혜택</a></li>
						<li><a href="#">이벤트 </a></li>
						<li><a href="#">주문배송 </a></li>
						<li><a href="#">고객센터</a></li>
					</ul>
				</nav>
			</div>

			<!-- 로고 및 검색 -->
			<div class="top2">
				<img class="m-i-n-i-i-logo"
					src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="미니문고 로고">
				<div class="search">
					<!-- 검색창 -->
					<div class="search-container">
						<form action="<%=request.getContextPath()%>/categories"
							method="GET"
							style="display: flex; align-items: center; justify-content: center;">
							<input type="text" name="query" placeholder="도서 제목, 저자 등 검색"
								class="search-input" required>
							<button type="submit" class="search-button">검색</button>
							<script>
    function goToMyPage() {
        var userId = "<%=session.getAttribute("idKey") != null ? session.getAttribute("idKey") : ""%>";
        if (userId) {
            window.location.href = "<%=request.getContextPath()%>/myPage.jsp";
        } else {
            alert("로그인 후 이용 가능합니다.");
            window.location.href = "<%=request.getContextPath()%>/LoginForm.jsp";
									}
								}
							</script>
							<img class="one" src="img/MainPage_img/removebg-preview-2.png" alt="장바구니 이미지"> 
							<img class="two" src="img/MainPage_img/1.png" alt="마이페이지 아이콘" onclick="goToMyPage()">

						</form>
					</div>

				</div>

				<!-- 네비게이션 바 -->
				<div class="top_3">
					<div class="line1">
						<img class="line" src="img/MainPage_img/line-27.svg" alt="라인 이미지">
					</div>
					<div class="navbar">
						<div class="navbar1">
							<a href="search.jsp">평점/리뷰 게시판</a>
						</div>
						<div class="navbar2">
							<a href="<%=request.getContextPath()%>/categories">도서검색</a>
						</div>
						<div class="navbar3">베스트리뷰</div>
						<div class="navbar4">이달의 책</div>
						<div class="navbar5">장르</div>
						<div class="navbar6">추천</div>
						<div class="navbar7">PICKS</div>
					</div>

					<div class="top_3_right">
						<div class="plus_line">
							<div class="plus">
								<div class="plus1"></div>
								<div class="plus-circle">+</div>
							</div>
						</div>
						<div class="member">
							<%
                     if (id != null) {
                     %>
							<span class="member-id"><%=id%>님</span> 방문해 주셔서 감사합니다.
							<%
                     } else {
                     %>
							로그인 하신 후 이용해 주세요.
							<%
                     }
                     %>
						</div>
					</div>
				</div>
			</div>
	</header>

	<!-- 본문 콘텐츠 -->
	<div class="banner">

		<div class="slider">
			<img src="img/MainPage_img/adv1.png" alt="광고 이미지 1"> <img
				src="img/MainPage_img/adv2.png" alt="광고 이미지 2"> <img
				src="img/MainPage_img/adv3.png" alt="광고 이미지 3"> <img
				src="img/MainPage_img/adv4.png" alt="광고 이미지 4">
		</div>
		<script>
const slides = document.querySelectorAll('.slider img');
let currentIndex = 0;

// 슬라이드 전환 함수
function changeSlide() {
    const currentSlide = slides[currentIndex];
    const nextIndex = (currentIndex + 1) % slides.length;
    const nextSlide = slides[nextIndex];

    // 현재 이미지를 왼쪽으로 밀어내기
    currentSlide.classList.remove('active');

    // 새 이미지를 오른쪽 끝에서 왼쪽으로 밀어넣기
    nextSlide.classList.add('active');

    // 인덱스를 업데이트
    currentIndex = nextIndex;
}

// 첫 번째 이미지가 보이도록 설정
slides[currentIndex].classList.add('active');

// 3초마다 슬라이드 전환
setInterval(changeSlide, 5000);

</script>
	</div>

	<p class="bestseller">
		리뷰로 보는 화제의 도서 <span class="top-text">TOP</span> 리스트!
	</p>
	<div class="banner2">

		<%
      System.out.println("JSP: /topBooks 호출 전");
      try {
         RequestDispatcher dispatcher = request.getRequestDispatcher("/topBooks");
         dispatcher.include(request, response);
         System.out.println("JSP: /topBooks 호출 완료");
      } catch (Exception e) {
         System.err.println("JSP에서 예외 발생: " + e.getMessage());
         e.printStackTrace();
      }

      // 세션에서 데이터 확인
      List<String[]> topBooks = (List<String[]>) session.getAttribute("topBooks");
      if (topBooks == null) {
         out.println("<p>topBooks 데이터가 없습니다.</p>");
         System.out.println("JSP: 세션에서 topBooks 데이터가 null입니다.");
      } else {
      %>
		<ul class="horizontal-list">
			<%
    if (topBooks != null && !topBooks.isEmpty()) {
        int rank = 1; // 순위 변수 초기화
        for (String[] bookData : topBooks) {
            String bookTitle = bookData[0];
            int reviewCount = Integer.parseInt(bookData[1]);

            // Book 객체 생성 및 이미지 URL 가져오기
            Book book = new Book(); // 적절한 생성자로 대체
            book.setTitle(bookTitle);
            book.setCover(AladinApiService.getBookCover(bookTitle)); // Aladin API로 이미지 URL 설정
            
            // 1위에만 추가 클래스 적용
            String rankClass = (rank == 1) ? "rank-number first-rank" : "rank-number";
    %>
			<li>
				<!-- 순위 번호 표시 -->
				<div class="<%= rankClass %>"><%= rank %></div> <!-- 도서 이미지 --> <a
				href="results?title=<%= URLEncoder.encode(book.getTitle(), "UTF-8") %>">
					<img src="<%= book.getCover() %>" alt="도서 표지" class="book-cover">
			</a> <!-- 도서 제목 --> <a
				href="results?title=<%= URLEncoder.encode(book.getTitle(), "UTF-8") %>">
					<strong><%= book.getTitle() %></strong>
			</a>
			</li>
			<%
        rank++; // 순위 증가
        }
    } else {
    %>
			<p>데이터가 없습니다.</p>
			<%
    }
    %>
		</ul>


		<%
      }
      %>

		<!-- 푸터 -->
		<footer>
			<div class="footer_address">
				<hr>
				<br> 공동대표이사 : 박민희, 최진솔, 정현오 | 서울특별시 종로구 종로 1 | 사업자등록번호 :
				102-81-11670 대표전화 : 1544-1900(발신자 부담전화) | FAX : 0502-987-5711(지역번호
				공통) | 서울특별시 통신판매업신고번호 : 제 653호 사업자정보확인 <br> © MINII BOOK CENTRE

			</div>
		</footer>
</body>
</html>