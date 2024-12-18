package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.ReviewDAO;
import model.Book;
import model.ReviewDTO;
import utils.APIClient;

@WebServlet("/search")
public class BookSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ALADIN_TTB_KEY = "ttbdongan01250115001"; // 알라딘 API 키

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            request.setAttribute("error", "검색어를 입력해주세요.");
            request.getRequestDispatcher("search.jsp").forward(request, response);
            return;
        }

        // 검색어 UTF-8 인코딩
        String encodedQuery = URLEncoder.encode(query, "UTF-8");

        // 알라딘 API URL 생성
        String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" + ALADIN_TTB_KEY
                + "&Query=" + encodedQuery
                + "&QueryType=Keyword&MaxResults=1&start=1&SearchTarget=Book&output=JS&Version=20131101";

        // 디버깅용 출력
        System.out.println("API URL: " + apiUrl);

        // API 호출
        String apiResponse = APIClient.fetch(apiUrl);

        if (apiResponse == null || apiResponse.trim().isEmpty()) {
            request.setAttribute("error", "API 응답이 없습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("search.jsp").forward(request, response);
            return;
        }

        // JSON 데이터 파싱
        JSONObject jsonResponse = new JSONObject(apiResponse);
        JSONArray items = jsonResponse.optJSONArray("item");

        if (items == null || items.isEmpty()) {
            request.setAttribute("error", "검색 결과가 없습니다.");
            request.getRequestDispatcher("search.jsp").forward(request, response);
            return;
        }

        // 첫 번째 검색 결과를 Book 객체로 변환
        JSONObject item = items.getJSONObject(0);
        Book book = new Book(
                item.optString("title", "정보 없음"),
                item.optString("author", "정보 없음"),
                item.optString("publisher", "정보 없음"),
                item.optString("pubDate", "정보 없음"),
                item.optString("cover", "")  // 표지 이미지 URL 추가
        );

        // 해당 도서에 대한 리뷰 목록 처리
        ReviewDAO reviewDAO = new ReviewDAO();
        List<ReviewDTO> reviews = reviewDAO.getReviewsByBookTitle(book.getTitle());

        // JSP로 데이터 전달 (단일 책 정보 및 리뷰)
        request.setAttribute("book", book);            // 단일 도서 정보 전달
        request.setAttribute("reviews", reviews);      // 해당 도서의 리뷰 목록 전달
        request.getRequestDispatcher("results.jsp").forward(request, response);
    }
}
