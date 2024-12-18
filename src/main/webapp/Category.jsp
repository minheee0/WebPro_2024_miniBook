<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>카테고리별 도서 목록</title>
    <link rel="stylesheet" href="css/Category/category.css">
</head>
<body>
    <header>
        <div class="logo-container">
            <a href="<%= request.getContextPath() %>">
                <img src="img/MainPage_img/m-i-n-i-i-logo-1.png" alt="로고" class="logo">
            </a>
        </div>
    </header>

    <!-- 검색창 -->
    <div class="search-container" style="text-align: center; margin-top: 20px;">
        <form action="<%= request.getContextPath() %>/categories" method="GET">
            <input type="text" name="query" placeholder="도서 제목, 저자 등 검색" class="search-input" 
                   style="padding: 10px; width: 300px; font-size: 16px; border-radius: 5px; border: 1px solid #ccc;">
            <button type="submit" class="search-button" 
                    style="padding: 10px 20px; font-size: 16px; margin-left: 10px; cursor: pointer; border-radius: 5px; background-color: #4CAF50; color: white; border: none;">
                검색
            </button>
        </form>
    </div>

    <!-- 메인 컨테이너 -->
    <div class="main-container" style="display: flex;">
        <!-- 카테고리 박스 -->
        <div class="category-box">
            <h2>카테고리</h2>
            <ul>
                <%
                    List<String> categories = (List<String>) request.getAttribute("categories");
                    String selectedCategory = (String) request.getAttribute("selectedCategory");
                    if (categories != null) {
                        for (String category : categories) {
                %>
                <li>
                    <a href="<%= request.getContextPath() %>/categories?category=<%= URLEncoder.encode(category, "UTF-8") %>" 
                       style="<%= category.equals(selectedCategory) ? "font-weight: bold;" : "" %>">
                        <%= category %>
                    </a>
                </li>
                <%
                        }
                    }
                %>
            </ul>
        </div>

        <!-- 도서 목록 -->
        <div class="content-box">
            <h3 class="page-title">
                <%
                    String searchQuery = (String) request.getAttribute("searchQuery");
                    if (searchQuery != null && !searchQuery.isEmpty()) {
                        out.print("'" + searchQuery + "' 검색 결과");
                    } else if (selectedCategory != null && !selectedCategory.isEmpty()) {
                        out.print("'" + selectedCategory + "' 카테고리 결과");
                    } else {
                        out.print("도서 목록");
                    }
                %>
            </h3>
            <%
                List<Book> books = (List<Book>) request.getAttribute("books");
                if (books != null && !books.isEmpty()) {
            %>
            <div id="book-list" class="book-list-container">
                <%
                    for (Book book : books) {
                        String encodedTitle = URLEncoder.encode(book.getTitle(), "UTF-8");
                %>
                <div class="book-item">
                    <a href="<%= request.getContextPath() %>/results?title=<%= encodedTitle %>">
                        <img src="<%= book.getCover() %>" alt="Book Cover" class="book-cover">
                    </a>
                    <div class="book-info">
                        <p>
                            <a href="<%= request.getContextPath() %>/results?title=<%= encodedTitle %>">
                                <strong>제목:</strong> <%= book.getTitle() %>
                            </a>
                        </p>
                        <p><strong>저자:</strong> <%= book.getAuthor() %></p>
                        <p><strong>출판사:</strong> <%= book.getPublisher() %></p>
                        <p><strong>출판년도:</strong> <%= book.getPubDate() %></p>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
            <%
                } else {
            %>
            <p>검색 결과가 없습니다.</p>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>
