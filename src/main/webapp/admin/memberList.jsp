<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="member.MemberDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
    <link rel="stylesheet" href="../css/admin/memberList.css">
</head>
<body>
    <div class="back-to-admin">
        <a href="<%= request.getContextPath() %>/admin/admin.jsp">관리자 페이지로 가기</a>
    </div>
    <h1>회원 목록 </h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>비밀번호</th>
                <th>이름</th>
                <th>이메일</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<MemberDTO> members = (List<MemberDTO>) request.getAttribute("members");
                if (members != null && !members.isEmpty()) {
                    for (MemberDTO member : members) {
            %>
            <tr>
                <td><%= member.getId() %></td>
                <td><%= member.getPassword() %></td>
                <td><%= member.getName() %></td>
                <td><%= member.getEmail() %></td>
                
                <td>
                <a href="admin?section=editMember&id=<%= member.getId() %>">
                 <button type="button">수정</button>
                </a>
            </td>

                
    
                <td>
                    <form action="/team444/admin/admin" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= member.getId() %>">
                        <button type="submit">삭제</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="4">회원 정보가 없습니다.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
