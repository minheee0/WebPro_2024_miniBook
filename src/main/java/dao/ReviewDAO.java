package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import member.JDBCUtil;
import model.ReviewDTO;

/**
 * ReviewDAO: 리뷰 데이터를 관리하는 DAO 클래스
 */
public class ReviewDAO {

	// 리뷰 저장
	public boolean saveReview(ReviewDTO review) {
		String sql = "INSERT INTO reviews (book_title, user_id, rating, review_content, created_at) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, review.getBookTitle());
			pstmt.setString(2, review.getUserId());
			pstmt.setInt(3, review.getRating());
			pstmt.setString(4, review.getReviewContent());
			pstmt.setTimestamp(5, Timestamp.valueOf(review.getCreatedAt()));

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 리뷰 삭제 (도서 제목과 리뷰 내용으로 삭제)
	public boolean deleteReview(String bookTitle, String reviewContent) {
		String sql = "DELETE FROM reviews WHERE book_title = ? AND review_content = ?";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, bookTitle);
			pstmt.setString(2, reviewContent);

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 리뷰 삭제 (리뷰 ID로 삭제)
	public boolean deleteReviewById(int reviewId) {
		String sql = "DELETE FROM reviews WHERE review_id = ?";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, reviewId);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 리뷰 수정 (리뷰 ID를 기준으로 수정)
	public boolean updateReviewById(ReviewDTO review) {
		String sql = "UPDATE reviews SET book_title = ?, review_content = ?, rating = ? WHERE review_id = ?";

		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, review.getBookTitle());
			pstmt.setString(2, review.getReviewContent());
			pstmt.setInt(3, review.getRating());
			pstmt.setInt(4, review.getReviewId());

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 리뷰 수정 (ID 또는 도서 제목으로 수정)
	public boolean updateReview(ReviewDTO review) {
		String sql;
		boolean isUsingReviewId = review.getReviewId() > 0;

		if (isUsingReviewId) {
			sql = "UPDATE reviews SET book_title = ?, review_content = ?, rating = ? WHERE review_id = ?";
		} else {
			sql = "UPDATE reviews SET review_content = ?, rating = ?, created_at = ? WHERE book_title = ?";
		}

		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			if (isUsingReviewId) {
				pstmt.setString(1, review.getBookTitle());
				pstmt.setString(2, review.getReviewContent());
				pstmt.setInt(3, review.getRating());
				pstmt.setInt(4, review.getReviewId());
			} else {
				pstmt.setString(1, review.getReviewContent());
				pstmt.setInt(2, review.getRating());
				pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
				pstmt.setString(4, review.getBookTitle());
			}

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 특정 도서에 대한 리뷰 조회
	public List<ReviewDTO> getReviewsByBookTitle(String bookTitle) {
		List<ReviewDTO> reviews = new ArrayList<>();
		String sql = "SELECT review_id, book_title, user_id, rating, review_content, created_at FROM reviews WHERE book_title = ? ORDER BY created_at DESC";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, bookTitle);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					reviews.add(mapResultSetToReview(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}

	// 특정 사용자가 작성한 리뷰 조회
	public List<ReviewDTO> getReviewsByUserId(String userId) {
		List<ReviewDTO> reviews = new ArrayList<>();
		String sql = "SELECT review_id, book_title, user_id, rating, review_content, created_at FROM reviews WHERE user_id = ? ORDER BY created_at DESC";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					reviews.add(mapResultSetToReview(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}

	// 모든 리뷰 조회
	public List<ReviewDTO> getAllReviews() {
		List<ReviewDTO> reviews = new ArrayList<>();
		String sql = "SELECT review_id, book_title, user_id, rating, review_content, created_at FROM reviews ORDER BY created_at DESC";
		try (Connection conn = JDBCUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				reviews.add(mapResultSetToReview(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}

	// 특정 리뷰 조회
	public ReviewDTO getReviewById(int reviewId) {
		String sql = "SELECT review_id, book_title, user_id, rating, review_content, created_at FROM reviews WHERE review_id = ?";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, reviewId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToReview(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* 메인페이지에서 리뷰 순으로 도서 출력하는 부분 */
	// 리뷰 수 기준으로 베스트 도서 5개를 가져오는 메서드
	public List<String[]> getTopBooksByReviewCount() {
	    String sql = "SELECT book_title, COUNT(*) as review_count FROM reviews GROUP BY book_title ORDER BY review_count DESC LIMIT 5";
	    List<String[]> topBooks = new ArrayList<>();
	    System.out.println("ReviewDAO: 쿼리 실행 시작");

	    try (Connection conn = JDBCUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            String[] bookInfo = new String[2];
	            bookInfo[0] = rs.getString("book_title");
	            bookInfo[1] = String.valueOf(rs.getInt("review_count"));
	            topBooks.add(bookInfo);
	        }

	        System.out.println("ReviewDAO: " + topBooks.size() + "개의 책 데이터 로드 완료");
	    } catch (SQLException e) {
	        System.err.println("ReviewDAO: SQL 오류 발생 - " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.err.println("ReviewDAO: 일반 오류 발생 - " + e.getMessage());
	        e.printStackTrace();
	    }

	    return topBooks;
	}


	// ResultSet을 ReviewDTO로 매핑
	private ReviewDTO mapResultSetToReview(ResultSet rs) throws SQLException {
		ReviewDTO review = new ReviewDTO();
		review.setReviewId(rs.getInt("review_id"));
		review.setBookTitle(rs.getString("book_title"));
		review.setUserId(rs.getString("user_id"));
		review.setRating(rs.getInt("rating"));
		review.setReviewContent(rs.getString("review_content"));
		review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		return review;
	}
}
