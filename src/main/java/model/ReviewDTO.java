package model;

import java.time.LocalDateTime;

public class ReviewDTO {
    private int reviewId; // 리뷰 ID (고유 식별자)
    private String bookTitle; // 도서 제목
    private String userId; // 사용자 ID
    private int rating; // 평점
    private String reviewContent; // 리뷰 내용
    private LocalDateTime createdAt; // 리뷰 작성 시간

    // 기본 생성자
    public ReviewDTO() {
    }

    // 모든 필드를 받는 생성자
    public ReviewDTO(int reviewId, String bookTitle, String userId, int rating, String reviewContent, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.bookTitle = bookTitle;
        this.userId = userId;
        this.rating = rating;
        this.reviewContent = reviewContent;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now(); // 작성 시간 기본값 설정
    }

    // createdAt 없이 생성하는 생성자 (기본값: 현재 시간)
    public ReviewDTO(String bookTitle, String userId, int rating, String reviewContent) {
        this(0, bookTitle, userId, rating, reviewContent, LocalDateTime.now());
    }

    // Getter 및 Setter 메서드
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString 메서드 (디버깅 및 로깅용)
    @Override
    public String toString() {
        return "ReviewDTO{" +
                "reviewId=" + reviewId +
                ", bookTitle='" + bookTitle + '\'' +
                ", userId='" + userId + '\'' +
                ", rating=" + rating +
                ", reviewContent='" + reviewContent + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
