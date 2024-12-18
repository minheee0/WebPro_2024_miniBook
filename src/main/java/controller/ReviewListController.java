package controller;

import dao.ReviewDAO;
import model.ReviewDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/admin/reviewList")
public class ReviewListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 리뷰 DAO 생성
        ReviewDAO dao = new ReviewDAO();

        // 모든 리뷰 가져오기
        List<ReviewDTO> reviews = dao.getAllReviews();

        // JSP로 전달하기 위해 request 속성 설정
        request.setAttribute("reviews", reviews);

        // reviewList.jsp로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/reviewList.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");

        if ("update".equalsIgnoreCase(action)) {
            handleUpdate(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            handleDelete(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=invalid_action");
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reviewId = request.getParameter("reviewId");

        if (reviewId != null) {
            try {
                int id = Integer.parseInt(reviewId);
                ReviewDAO dao = new ReviewDAO();
                boolean isDeleted = dao.deleteReviewById(id);

                if (isDeleted) {
                    response.sendRedirect(request.getContextPath() + "/admin/reviewList?success=delete");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=delete_failed");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=invalid_id");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=missing_id");
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            String bookTitle = request.getParameter("bookTitle");
            String userId = request.getParameter("userId");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String reviewContent = request.getParameter("reviewContent");

            // LocalDateTime.now()를 기본으로 작성 시간 업데이트
            ReviewDTO review = new ReviewDTO(reviewId, bookTitle, userId, rating, reviewContent, LocalDateTime.now());
            ReviewDAO dao = new ReviewDAO();
            boolean isUpdated = dao.updateReview(review);

            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/admin/reviewList?success=update");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/editReview?reviewId=" + reviewId + "&error=update_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=invalid_input");
        }
    }
}
