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
  <h4>실시간 검색어</h4>
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
    query = "select * from Ranking order by SEARCH_CNT desc limit 10;";
    
    
   %>
   <button type="button" onclick="location.href='#top'">위로 올라가기</button>
</body>
</html>