package admin;

import dao.ReviewDAO;
import model.ReviewDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/editReview")
public class EditReviewController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewId = request.getParameter("reviewId");

        if (reviewId != null && !reviewId.isEmpty()) {
            try {
                int id = Integer.parseInt(reviewId);
                ReviewDAO dao = new ReviewDAO();
                ReviewDTO review = dao.getReviewById(id);

                if (review != null) {
                    request.setAttribute("review", review);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/editReview.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=not_found");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=invalid_id");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=missing_id");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 요청 및 응답 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try {
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            String bookTitle = request.getParameter("bookTitle");
            String reviewContent = request.getParameter("reviewContent");
            int rating = Integer.parseInt(request.getParameter("rating"));

            ReviewDAO dao = new ReviewDAO();
            ReviewDTO review = new ReviewDTO(reviewId, bookTitle, null, rating, reviewContent, null);

            if (dao.updateReviewById(review)) {
                response.sendRedirect(request.getContextPath() + "/admin/reviewList?success=update");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/editReview?reviewId=" + reviewId + "&error=update_failed");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/reviewList?error=exception");
        }
    }

    
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            String bookTitle = request.getParameter("bookTitle");
            String userId = request.getParameter("userId");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String reviewContent = request.getParameter("reviewContent");

            ReviewDTO review = new ReviewDTO(reviewId, bookTitle, userId, rating, reviewContent, null);
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

