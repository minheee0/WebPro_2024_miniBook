package member;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register.do")
public class JoinController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 요청과 응답 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // 요청에서 회원가입 데이터 가져오기
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        int userType = Integer.parseInt(request.getParameter("user_type")); // user_type 값 추가

        // DTO 객체에 데이터 설정
        MemberDTO mDto = new MemberDTO();
        mDto.setId(id);
        mDto.setPassword(password);
        mDto.setName(name);
        mDto.setEmail(email);
        mDto.setUserType(userType); // user_type 설정

        // DAO를 통해 회원가입 처리
        MemberDAO mDao = new MemberDAO();
        boolean insertCheck = mDao.memberInsert(mDto);

        // 결과에 따라 페이지 이동
        if (insertCheck) {
            // 회원가입 성공 시
            request.setAttribute("joinResult", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp"); // 성공 페이지로 이동
            dispatcher.forward(request, response);
        } else {
            // 회원가입 실패 시
            request.setAttribute("joinResult", false);
            RequestDispatcher dispatcher = request.getRequestDispatcher("LoginForm.jsp"); // 실패 페이지로 이동
            dispatcher.forward(request, response);
        }
    }
}
