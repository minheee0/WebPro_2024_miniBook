package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.MemberDAO;
import member.MemberDTO;

@WebServlet("/updateInfo")
public class UpdateInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 세션에서 사용자 ID 확인
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("idKey");

        // 로그인되지 않았다면 로그인 페이지로 리디렉션
        if (userId == null || userId.trim().isEmpty()) {
            response.sendRedirect("LoginForm.jsp");
            return;
        }

        // 폼에서 전송된 데이터 가져오기
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // 새 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
            // 비밀번호 불일치 시 에러 메시지 표시
            request.setAttribute("error", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("updateInfo.jsp").forward(request, response);
            return;
        }

        // 사용자 정보 수정
        MemberDAO memberDAO = new MemberDAO();
        MemberDTO member = new MemberDTO();
        member.setId(userId);
        member.setName(name);
        member.setEmail(email);

        // 새 비밀번호가 입력된 경우에만 비밀번호 업데이트 처리
        if (newPassword != null && !newPassword.isEmpty()) {
            member.setPassword(newPassword);  // 평문 비밀번호 설정
        }

        // 정보 수정 처리
        boolean isUpdated = memberDAO.updateMemberInfo(member);
        if (isUpdated) {
            // 수정 후 세션에 새 정보 저장
            session.setAttribute("name", name);
            session.setAttribute("email", email);
            response.sendRedirect("myPage");  // 수정 후 마이페이지로 리디렉션
        } else {
            // 수정 실패 시 에러 메시지 전달
            request.setAttribute("error", "정보 수정에 실패했습니다.");
            request.getRequestDispatcher("updateInfo.jsp").forward(request, response);
        }
    }
}



