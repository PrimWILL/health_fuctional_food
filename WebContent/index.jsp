<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@page import="health_functional_food.WebPageVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
	<h1>건기식보</h1>
	<h4>건강 기능 식품 검색</h4>
	<p>원료 혹은 회사를 검색하세요.</p>
	<% WebPageVO vo = new WebPageVO(); %>
	<p>
	  원료검색 <input type="text" name = "itemName" style = "width:200px" id="itemName"></input>
	  <button type = "submit" onclick="location.href='itemShow.jsp'">검색</button>
	  <br>
	  회사검색 <input type="text" name="companyName" style = "width:200px" id="companyName"></input>
	  <button type = "submit" onclick="location.href='companyShow.jsp'">검색</button>
	</p>
</body>
</html>