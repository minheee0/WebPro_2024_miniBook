package member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login.do")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 요청과 응답 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // 요청에서 아이디, 비밀번호, 사용자 유형(user_type) 가져오기
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        int userType = Integer.parseInt(request.getParameter("user_type")); // user_type 추가

        // DAO를 통해 로그인 처리
        MemberDAO mDao = new MemberDAO();
        boolean loginCheck = mDao.loginCheckWithUserType(id, password, userType); // user_type 포함 메서드 호출

        // 로그인 성공 여부에 따라 페이지 이동
        if (loginCheck) {
            HttpSession session = request.getSession();
            session.setAttribute("idKey", id); // 세션에 사용자 아이디 저장
            session.setAttribute("userType", userType); // 세션에 사용자 유형 저장

            if (userType == 1) {
                // 관리자 로그인 성공 시
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
                dispatcher.forward(request, response);
            } else if (userType == 2) {
                // 회원 로그인 성공 시
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // 로그인 실패 시
            request.setAttribute("loginResult", false);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loginFail.jsp");
            dispatcher.forward(request, response);
        }
    }
}
