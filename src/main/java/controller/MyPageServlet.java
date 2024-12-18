package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ReviewDAO;
import model.ReviewDTO;

@WebServlet("/myPage")
public class MyPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// 요청과 응답 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
    	
        HttpSession session = request.getSession();

        // 세션에서 사용자 ID 확인
        String userId = (String) session.getAttribute("idKey");
        if (userId == null || userId.trim().isEmpty()) {
            // 로그인되지 않은 경우 로그인 페이지로 리디렉션
            response.sendRedirect("LoginForm.jsp");
            return;
        }

        List<ReviewDTO> reviews = new ArrayList<>();
        try {
            // ReviewDAO 객체를 통해 리뷰 조회
            ReviewDAO reviewDAO = new ReviewDAO();
            reviews = reviewDAO.getReviewsByUserId(userId);

            // 로그: 리뷰 사이즈 출력
            System.out.println("User ID: " + userId + ", Number of Reviews: " + (reviews != null ? reviews.size() : 0));

        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            System.err.println("Error retrieving reviews for user ID: " + userId);
            e.printStackTrace();
        }

        // JSP에 리뷰 데이터 전달
        request.setAttribute("reviews", reviews != null ? reviews : new ArrayList<>());

        // JSP로 포워딩
        request.getRequestDispatcher("/myPage.jsp").forward(request, response);
    }
}
