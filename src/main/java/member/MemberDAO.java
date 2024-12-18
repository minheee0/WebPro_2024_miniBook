package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

	// 로그인 확인 (user_type 포함)
	public boolean loginCheckWithUserType(String id, String password, int userType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean loginCon = false;

		try {
			conn = JDBCUtil.getConnection();
			String strQuery = "SELECT id FROM users WHERE id = ? AND password = ? AND user_type = ?";
			pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setInt(3, userType);
			rs = pstmt.executeQuery();
			loginCon = rs.next(); // 로그인 성공 여부
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return loginCon;
	}

	// 회원 가입 메서드
	public boolean memberInsert(MemberDTO mDTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;

		try {
			conn = JDBCUtil.getConnection();
			String strQuery = "INSERT INTO users (id, password, email, name, user_type) VALUES (?, ?, ?, ?, ?)";
																													
			pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, mDTO.getId());
			pstmt.setString(2, mDTO.getPassword());
			pstmt.setString(3, mDTO.getEmail());
			pstmt.setString(4, mDTO.getName());
			pstmt.setInt(5, mDTO.getUserType()); // user_type 값 설정

			int count = pstmt.executeUpdate();
			flag = count == 1; // 삽입 성공 여부 확인
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
		} finally {
			JDBCUtil.close(null, pstmt, conn);
		}
		return flag;
	}

	// 비밀번호 변경 메서드
	public boolean updatePassword(String id, String oldPassword, String newPassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JDBCUtil.getConnection();

			// 기존 비밀번호 확인
			String checkQuery = "SELECT id FROM users WHERE id = ? AND password = ?";
			pstmt = conn.prepareStatement(checkQuery);
			pstmt.setString(1, id);
			pstmt.setString(2, oldPassword);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 기존 비밀번호가 맞는 경우
				// 비밀번호 변경
				String updateQuery = "UPDATE users SET password = ? WHERE id = ?";
				pstmt = conn.prepareStatement(updateQuery);
				pstmt.setString(1, newPassword);
				pstmt.setString(2, id);

				int rowsUpdated = pstmt.executeUpdate();
				return rowsUpdated > 0; // 변경 성공 여부 반환
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return false;
	}

	// 모든 회원 조회 메서드
	public List<MemberDTO> getAllMembers() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberDTO> members = new ArrayList<>();

		try {
			conn = JDBCUtil.getConnection(); // JDBCUtil은 DB 연결을 처리
			String query = "SELECT id, password, email, name FROM users";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				members.add(member);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return members;
	}

	// 삭제
	public boolean deleteMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = JDBCUtil.getConnection();
			String query = "DELETE FROM users WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0; // 삭제 성공 여부 반환
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.close(null, pstmt, conn);
		}
		return false;
	}

	// 특정 회원 정보 조회
	public MemberDTO getMemberById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDTO member = null;

		try {
			conn = JDBCUtil.getConnection();
			String query = "SELECT id, name, email, password FROM users WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new MemberDTO();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return member;
	}

	// 회원 정보 업데이트
	public boolean updateMember(MemberDTO member) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = JDBCUtil.getConnection();
			String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getId());

			int rowsUpdated = pstmt.executeUpdate();
			return rowsUpdated > 0; // 업데이트 성공 여부 반환
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.close(null, pstmt, conn);
		}
		return false;
	}

	// 회원 정보 수정 메서드
	public boolean updateMemberInfo(MemberDTO memberDTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = JDBCUtil.getConnection();
			String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getEmail());
			pstmt.setString(3, memberDTO.getPassword()); // 평문 비밀번호 사용
			pstmt.setString(4, memberDTO.getId());

			int rowsUpdated = pstmt.executeUpdate();
			return rowsUpdated > 0; // 업데이트 성공 여부 반환
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, pstmt, conn);
		}
		return false;
	}

}
