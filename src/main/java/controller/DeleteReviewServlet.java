package controller;

import dao.ReviewDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteReview")
public class DeleteReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 문자 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        // 리뷰 ID 가져오기
        String reviewIdStr = request.getParameter("reviewId");

        if (reviewIdStr == null || reviewIdStr.trim().isEmpty()) {
            System.out.println("Invalid reviewId: null or empty");
            response.sendRedirect(request.getContextPath() + "/myPage?error=invalidReviewId");
            return;
        }

        int reviewId;
        try {
            reviewId = Integer.parseInt(reviewIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid reviewId format: " + reviewIdStr);
            response.sendRedirect(request.getContextPath() + "/myPage?error=invalidReviewIdFormat");
            return;
        }

        // DAO를 통해 리뷰 삭제
        ReviewDAO reviewDAO = new ReviewDAO();
        boolean success = reviewDAO.deleteReviewById(reviewId);

        if (success) {
            System.out.println("Review with reviewId: " + reviewId + " deleted successfully.");
            response.sendRedirect(request.getContextPath() + "/myPage?message=reviewDeleted");
        } else {
            System.out.println("Failed to delete review with reviewId: " + reviewId);
            response.sendRedirect(request.getContextPath() + "/myPage?error=deleteFailed");
        }
    }
}
