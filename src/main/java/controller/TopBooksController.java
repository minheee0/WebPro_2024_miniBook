package controller;

import dao.ReviewDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/topBooks")
public class TopBooksController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TopBooksController: 호출됨");
        try {
            ReviewDAO reviewDAO = new ReviewDAO();
            List<String[]> topBooks = reviewDAO.getTopBooksByReviewCount();

            if (topBooks == null || topBooks.isEmpty()) {
                System.out.println("TopBooksController: topBooks 데이터가 없습니다.");
            } else {
                System.out.println("TopBooksController: " + topBooks.size() + "개의 데이터가 로드되었습니다.");
            }

            request.setAttribute("topBooks", topBooks);
            request.getSession().setAttribute("topBooks", topBooks); // 세션에 데이터 저장
        } catch (Exception e) {
            System.err.println("TopBooksController: 예외 발생 - " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("TopBooksController에서 예외 발생", e);
        }
    }
}
