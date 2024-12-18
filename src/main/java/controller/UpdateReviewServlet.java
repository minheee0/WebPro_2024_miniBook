package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReviewDAO;
import model.ReviewDTO;

@WebServlet("/updateReview")
public class UpdateReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String reviewIdStr = request.getParameter("reviewId");
        String bookTitle = request.getParameter("bookTitle");
        String reviewContent = request.getParameter("reviewContent");
        String ratingStr = request.getParameter("rating");

        try {
            int reviewId = Integer.parseInt(reviewIdStr);
            int rating = Integer.parseInt(ratingStr);

            ReviewDAO dao = new ReviewDAO();
            ReviewDTO review = new ReviewDTO(reviewId, bookTitle, null, rating, reviewContent, null);

            if (dao.updateReviewById(review)) {
                response.sendRedirect("myPage.jsp?success=update");
            } else {
                response.sendRedirect("updateReview.jsp?error=update_failed");
            }
        } catch (Exception e) {
            response.sendRedirect("updateReview.jsp?error=exception");
        }
    }
}
