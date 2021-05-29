<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="health_functional_food.DomThread" %>
<%@page import="health_functional_food.Search" %>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
  <h1>건기식보</h1>
  <h4>회사 검색 결과</h4>
   <%
   
   String companyName = request.getParameter("companyName");
   
   PreparedStatement p=null;
	ResultSet r = null;
	Connection conn = null;
   
   String query;
   Search search = new Search();
   String connectionUrl = "jdbc:postgresql:health_functional_food";
	String user = "postgres";
	String password = "1234";

	conn = DriverManager.getConnection(connectionUrl, user, password);  
    query = "select * from Gmp where BSSH_NM like '%"+companyName+"%';";
	
	p = conn.prepareStatement(query);
	r = p.executeQuery();
	out.print("<p>다음은 '" + companyName + "' 에 대한 GMP 지정 여부 검색 결과입니다.</p>");
	out.print("<table border='1'>");
	out.print("<th>업소명</th>");
	out.print("<th>업소고유번호</th>");
	out.print("<th>GMP 지정번호</th>");
	out.print("<th>대표자명</th>");
	out.print("<th>GMP 취소일자</th>");
	
	while(r.next()) {
		out.print("<tr>");
		out.print("<td>"+r.getString(1)+"</td>");
		out.print("<td>"+r.getString(2)+"</td>");
		out.print("<td>"+r.getString(3)+"</td>");
		out.print("<td>"+r.getString(4)+"</td>");
		out.print("<td>"+r.getString(5)+"</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	
	conn = DriverManager.getConnection(connectionUrl, user, password);  
    query = "select * from Retail where BSSH_NM like '%"+companyName+"%';";
	
	p = conn.prepareStatement(query);
	r = p.executeQuery();
	
	out.print("<br/><br/><p>다음은 '" + companyName + "' 에 대한 건강기능식품판매업 등록 여부 검색 결과입니다.</p>");
	out.print("<table border='2'>");
	out.print("<th>허가일자</th>");
	out.print("<th>업소명</th>");
	out.print("<th>전화번호</th>");
	out.print("<th>인허가 번호</th>");
	out.print("<th>대표자명</th>");
	out.print("<th>기관명</th>");
	out.print("<th>주소</th>");
	
	while(r.next()) {
		out.print("<tr>");
		out.print("<td>"+r.getString(1)+"</td>");
		out.print("<td>"+r.getString(2)+"</td>");
		out.print("<td>"+r.getString(3)+"</td>");
		out.print("<td>"+r.getString(4)+"</td>");
		out.print("<td>"+r.getString(5)+"</td>");
		out.print("<td>"+r.getString(6)+"</td>");
		out.print("<td>"+r.getString(7)+"</td>");
		out.print("</tr>");
	}
	out.print("</table>");
   %>
   <br/>
   <br/>
   <form action='index.jsp'>
	  <input type='submit' value="다시 검색하기"></input>
   </form>
</body>
</html>