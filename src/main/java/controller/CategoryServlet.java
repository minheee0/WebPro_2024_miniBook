package controller;

import model.Book;
import utils.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {
    private static final String ALADIN_TTB_KEY = "ttbdongan01250115001";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> categories = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        String selectedCategory = request.getParameter("category");
        String searchQuery = request.getParameter("query"); // 검색어

        try {
            // 카테고리 리스트 초기화
            categories.add("소설");
            categories.add("자기계발");
            categories.add("역사");
            categories.add("경제");
            categories.add("과학");
            categories.add("예술");
            categories.add("철학");

            if ((selectedCategory != null && !selectedCategory.isEmpty()) ||
                (searchQuery != null && !searchQuery.isEmpty())) {

                String queryParam = searchQuery != null && !searchQuery.isEmpty() ? searchQuery : selectedCategory;
                System.out.println("Query: " + queryParam);

                // API 호출
                String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" + ALADIN_TTB_KEY
                        + "&Query=" + URLEncoder.encode(queryParam, "UTF-8")
                        + "&QueryType=Keyword&MaxResults=10&start=1&SearchTarget=Book&output=JS&Version=20131101";
                System.out.println("API URL: " + apiUrl);

                // API 응답 처리
                String apiResponse = APIClient.fetch(apiUrl);
                if (apiResponse != null && !apiResponse.trim().isEmpty()) {
                    JSONObject jsonResponse = new JSONObject(apiResponse);
                    JSONArray items = jsonResponse.optJSONArray("item");

                    if (items != null) {
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            books.add(new Book(
                                    item.optString("title", "정보 없음"),
                                    item.optString("author", "정보 없음"),
                                    item.optString("publisher", "정보 없음"),
                                    item.optString("pubDate", "정보 없음"),
                                    item.optString("cover", "")
                            ));
                        }
                    }
                }
            }

            // 데이터 전달
            request.setAttribute("categories", categories);
            request.setAttribute("books", books);
            request.setAttribute("selectedCategory", selectedCategory);
            request.setAttribute("searchQuery", searchQuery);

            if (books.isEmpty()) {
                request.setAttribute("error", "결과를 찾을 수 없습니다.");
            }

            request.getRequestDispatcher("/Category.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
