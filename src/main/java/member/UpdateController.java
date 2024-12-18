package member;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatePassword.do")
public class UpdateController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 요청 및 응답 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // 요청에서 파라미터 가져오기
        String id = request.getParameter("id");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // DAO 호출하여 비밀번호 변경 처리
        MemberDAO mDao = new MemberDAO();
        boolean isUpdated = mDao.updatePassword(id, oldPassword, newPassword);

        if (isUpdated) {
            // 비밀번호 변경 성공 시 index.jsp로 이동
            request.setAttribute("updateResult", "success");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } else {
            // 비밀번호 변경 실패 시 실패 페이지로 이동
            request.setAttribute("updateResult", "fail");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UpdateFail.jsp");
            dispatcher.forward(request, response);
        }
    }
}

