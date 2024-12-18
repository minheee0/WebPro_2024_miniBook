package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ReviewDTO;
import dao.ReviewDAO;

@WebServlet("/submitReview")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 요청 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        // 폼 데이터 가져오기
        String userId = request.getParameter("userId");
        String bookTitle = request.getParameter("bookTitle");
        String ratingStr = request.getParameter("rating");
        String reviewContent = request.getParameter("reviewContent");

        // 유효성 검사
        if (userId == null || bookTitle == null || ratingStr == null || reviewContent == null ||
            userId.trim().isEmpty() || bookTitle.trim().isEmpty() || ratingStr.trim().isEmpty() || reviewContent.trim().isEmpty()) {
            request.setAttribute("error", "모든 필드를 채워주세요.");
            request.getRequestDispatcher("addReview.jsp").forward(request, response);
            return;
        }

        // 평점 변환 및 검증
        int reviewRating;
        try {
            reviewRating = Integer.parseInt(ratingStr);
            if (reviewRating < 1 || reviewRating > 5) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "평점은 1에서 5 사이의 숫자여야 합니다.");
            request.getRequestDispatcher("addReview.jsp").forward(request, response);
            return;
        }

        // 현재 시간
        LocalDateTime createdAt = LocalDateTime.now();

        // ReviewDTO 객체 생성
        ReviewDTO review = new ReviewDTO(0, bookTitle, userId, reviewRating, reviewContent, createdAt);

        // 데이터베이스에 리뷰 저장
        ReviewDAO reviewDAO = new ReviewDAO();
        boolean isSaved = reviewDAO.saveReview(review);

        // 결과 처리
        if (isSaved) {
            request.setAttribute("bookTitle", bookTitle);
            request.getRequestDispatcher("reviewSuccess.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "리뷰 저장에 실패했습니다.");
            request.getRequestDispatcher("addReview.jsp").forward(request, response);
        }
    }
}

