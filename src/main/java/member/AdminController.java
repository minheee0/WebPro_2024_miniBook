package member;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/admin")
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 요청과 응답 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
    	
        String section = request.getParameter("section");

        if ("members".equals(section)) {
            MemberDAO dao = new MemberDAO();
            List<MemberDTO> members = dao.getAllMembers();
            request.setAttribute("members", members);
            request.getRequestDispatcher("/admin/memberList.jsp").forward(request, response);

        } else if ("editMember".equals(section)) {
            String id = request.getParameter("id");
            MemberDAO dao = new MemberDAO();
            MemberDTO member = dao.getMemberById(id);
            if (member != null) {
                request.setAttribute("member", member);
                request.getRequestDispatcher("/admin/EditMember.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/admin?section=members&error=not_found");
            }

        } else {
            request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 요청 및 응답 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            MemberDAO dao = new MemberDAO();
            MemberDTO member = new MemberDTO();
            member.setId(id);
            member.setName(name);
            member.setEmail(email);
            member.setPassword(password);

            boolean success = dao.updateMember(member);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/admin?section=members&success=update");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/admin?section=members&error=update");
            }

        } else if ("delete".equals(action)) {
            String id = request.getParameter("id");

            MemberDAO dao = new MemberDAO();
            boolean success = dao.deleteMember(id);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/admin?section=members&success=delete");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/admin?section=members&error=delete");
            }
        }
    }
}
