package controller;

import model.Book;
import model.ReviewDTO;
import dao.ReviewDAO;
import utils.APIClient;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/results")
public class ResultServlet extends HttpServlet {
    private static final String ALADIN_TTB_KEY = "ttbdongan01250115001";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
       
     // 디버깅용: title 파라미터 확인
        System.out.println("Received title parameter: " + title);

        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title parameter is missing or empty. Redirecting to categories.");
            response.sendRedirect(request.getContextPath() + "/categories");
            return;
        }

        try {
            // API 호출
            String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" + ALADIN_TTB_KEY
                    + "&Query=" + URLEncoder.encode(title, "UTF-8")
                    + "&QueryType=Title&MaxResults=1&start=1&SearchTarget=Book&output=JS&Version=20131101";
            String apiResponse = APIClient.fetch(apiUrl);

            if (apiResponse != null && !apiResponse.trim().isEmpty()) {
                JSONObject jsonResponse = new JSONObject(apiResponse);
                JSONObject item = jsonResponse.getJSONArray("item").getJSONObject(0);

                Book book = new Book(
                        item.optString("title", "정보 없음"),
                        item.optString("author", "정보 없음"),
                        item.optString("publisher", "정보 없음"),
                        item.optString("pubDate", "정보 없음"),
                        item.optString("cover", "")
                );

                // 리뷰 데이터 가져오기
                ReviewDAO reviewDAO = new ReviewDAO();
                List<ReviewDTO> reviews = reviewDAO.getReviewsByBookTitle(title);

                // 데이터 JSP로 전달
                request.setAttribute("book", book);
                request.setAttribute("reviews", reviews);
                request.getRequestDispatcher("/results.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/categories");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/categories");
        }
    }
}
