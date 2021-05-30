<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	<form action='itemShow.jsp' method='get'>
	  원료검색 <input type="text" name = "itemName" style = "width:200px" id="itemName"></input>
	  <input type='submit' value="검색"></input>
	</form>
	  <br>
	<form action='companyShow.jsp' method='get'>
	  회사검색 <input type="text" name="companyName" style = "width:200px" id="companyName"></input>
	  <input type='checkbox' name="gmp" value="gmp">GMP지정여부</input>
  	  <input type='checkbox' name='retail' value='retail'>건강기능식품판매업 등록 여부</input>
	  <input type='submit' value="검색"></input>
	</form>
</body>
</html>