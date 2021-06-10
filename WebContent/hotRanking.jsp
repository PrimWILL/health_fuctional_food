<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="health_functional_food.MainApp" %>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
  <h1 id = "top">건기식보</h1>
  <h4>인기 검색어</h4>
  <form action='index.jsp'>
	  <input type='submit' value="다시 검색하기"></input>
   </form>
   <%
   PreparedStatement p=null;
   ResultSet r = null;
   Connection conn = null;
   
   String query;
   String connectionUrl = "jdbc:postgresql:health_functional_food";
   String user = "postgres";
   String password = "1234";

	conn = DriverManager.getConnection(connectionUrl, user, password);  
    query = "select * from Ranking order by SEARCH_CNT desc, PRDCT_NM asc limit 10;";
    
    p = conn.prepareStatement(query);
    r = p.executeQuery();
    
    out.print("<p>다음은 인기 검색어입니다.</p>");
	out.print("<table border='1'>");
	out.print("<th>검색순위</th>");
	out.print("<th>검색어</th>");
	out.print("<th>검색횟수</th>");
	
	int rank = 1;
	while(r.next()) {
		out.print("<tr>");
		out.print("<td>"+rank+"위</td>");
		out.print("<td>"+r.getString(1)+"</td>");
		out.print("<td>"+r.getString(2)+"</td>");
		out.print("</tr>");
		rank++;
	}
	out.print("</table><br/><br/>");
   %>
   <button type="button" onclick="location.href='#top'">위로 올라가기</button>
</body>
</html>