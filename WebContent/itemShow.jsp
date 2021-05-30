<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="health_functional_food.DomThread" %>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
  <h1 id = "top">건기식보</h1>
  <h4>원료 검색 결과</h4>
  <form action='index.jsp'>
	  <input type='submit' value="다시 검색하기"></input>
   </form>
   <%
   
   String itemName = request.getParameter("itemName");
   
   PreparedStatement p=null;
	ResultSet r = null;
	Connection conn = null;
   
   String query;
   String connectionUrl = "jdbc:postgresql:health_functional_food";
	String user = "postgres";
	String password = "1234";

	conn = DriverManager.getConnection(connectionUrl, user, password);  
   query = "select * from acknowledgment natural full outer join item where prdct_nm like '%"+itemName+"%';";
	
	p = conn.prepareStatement(query);
	r = p.executeQuery();
	
	out.print("<p>다음은 '" + itemName + "' 에 대한 검색 결과입니다.</p>");
	out.print("<table border='1'>");
	out.print("<th>품목명</th>");
	out.print("<th>섭취시 주의사항</th>");
	out.print("<th>주된 기능성</th>");
	out.print("<th>일일섭취량 상한</th>");
	out.print("<th>업체명</th>");
	out.print("<th>주소</th>");
	out.print("<th>단위</th>");
	out.print("<th>일일섭취량 하한</th>");
	
	boolean cp = false;
	while(r.next()) {
		cp = true;
		out.print("<tr>");
		out.print("<td>"+r.getString(1)+"</td>");
		out.print("<td>"+r.getString(2)+"</td>");
		out.print("<td>"+r.getString(3)+"</td>");
		out.print("<td>"+r.getString(4)+"</td>");
		out.print("<td>"+r.getString(5)+"</td>");
		out.print("<td>"+r.getString(6)+"</td>");
		out.print("<td>"+r.getString(7)+"</td>");
		out.print("<td>"+r.getString(8)+"</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	
	if (cp == false) {
		out.print("검색 결과가 없습니다. 다시 검색하세요.");
	}
   %>
   <br/>
   <br/>
   <button type="button" onclick="location.href='#top'">위로 올라가기</button>
</body>
</html>